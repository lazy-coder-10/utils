package tech.appzilla.com.setmydate.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.content.FileProvider
import android.util.Log
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class GalleryUtils(context: Context) {

    var context: Context? = context

    companion object {var mCurrentPhotoPath: String? = null}


    //open camera
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(context!!.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile : File ? = null;
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context!!,
                    "com.example.android.fileprovider",
                    photoFile
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                (context as Activity).startActivityForResult(intent, AppConstants.CODE_PERMISSION_CAMERA)
            }

        }

    }

    @Throws(IOException::class)
            fun  createImageFile() : File  {
            // Create an image file name
            val timeStamp =  SimpleDateFormat("yyyyMMdd_HHmmss").format( Date());
            val imageFileName = "JPEG_" + timeStamp + "_";
            val storageDir = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            val image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = image.getAbsolutePath();

        Log.d("CURRENT_PATH",mCurrentPhotoPath)

            return image
        }


        //open gallery
    fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        (context as Activity).startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            AppConstants.CODE_PERMISSION_GALLERY
        )

    }

    //open image and video gallery with multiple selection
    fun openMultipleSelectGallery(selectionType: String) {
        val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = selectionType
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        (context as Activity).startActivityForResult(pickIntent, AppConstants.CODE_IMAGE_PICKER)
    }

}