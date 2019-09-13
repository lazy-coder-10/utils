package tech.appzilla.com.setmydate.Utils


import android.Manifest
import android.content.Context
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import android.content.Intent
import android.content.DialogInterface
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import tech.appzilla.com.setmydate.Interface.OnPermissionEventListner
import java.util.ArrayList


class PermissionManager {
    val MY_PERMISSIONS_REQUEST_ACCOUNTS = 100
    private var mListener: OnPermissionEventListner? = null //listener field
    var context: Context?
    var strPermission: String? = null
    var strPermissionCode: Int? = null

    constructor(strPermission: String, callbackInterface: OnPermissionEventListner, context: Context?, strPermissionCode: Int) {
        this.strPermission = strPermission
        this.mListener = callbackInterface
        this.context = context
        this.strPermissionCode = strPermissionCode
    }

    constructor(context: Activity) {
        this.context = context
    }


    /**
     * Requesting  permission
     * This uses single permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */
    fun requestPermission() {
        Dexter.withActivity(context as Activity?)
                .withPermission(strPermission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        // permission is granted
                        mListener!!.onPermissionGranted("Permission Granted", strPermissionCode)

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        mListener!!.onPermissionDenied("Permission Denied")
                        if (response.isPermanentlyDenied) {
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    /**
     * Requesting  permission
     * This uses Multiple permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            openSettings()
        })
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()

    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        (context as Activity).startActivityForResult(intent, 100)
    }

    fun checkAndRequestPermissions(context: AppCompatActivity): Boolean {

        val permissionLocation = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION)

        val permissionLocation1 = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        /* val permissionPhone = ContextCompat.checkSelfPermission(activity!!,
                 Manifest.permission.CALL_PHONE);*/
        /*

        int permissionContacts = ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS);

        int permissionRContacts = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);

        int permissionMediaControls = ContextCompat.checkSelfPermission(this,
                Manifest.permission.MEDIA_CONTENT_CONTROL);*/

        /* val permissionCamera = ContextCompat.checkSelfPermission(context as Activity,
                 Manifest.permission.CAMERA);
         val permissionStorage = ContextCompat.checkSelfPermission(context as Activity,
                 Manifest.permission.WRITE_EXTERNAL_STORAGE)

         val permissionReadStorage = ContextCompat.checkSelfPermission(context as Activity,
                 Manifest.permission.READ_EXTERNAL_STORAGE)
 */
        val permissionRecordAudio = ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO)


        val listPermissionsNeeded = ArrayList<String>()

        /*if (permissionPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }*/
        /*
        if (permissionContacts != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        }*/
       /* if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }*/
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionLocation1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        /*if (permissionRContacts != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (permissionMediaControls != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.MEDIA_CONTENT_CONTROL);
        }*/
        /* if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
         }*/

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context as Activity,
                    listPermissionsNeeded.toTypedArray(), MY_PERMISSIONS_REQUEST_ACCOUNTS)
            return false
        }

        return true
    }


    fun readWritePermissions() {

        val permissionStorage = ContextCompat.checkSelfPermission(context as Activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val permissionReadStorage = ContextCompat.checkSelfPermission(context as Activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        /*  val permissionRecordAudio = ContextCompat.checkSelfPermission(this,
                  Manifest.permission.RECORD_AUDIO)*/


        val listPermissionsNeeded = ArrayList<String>()
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context as Activity,
                    listPermissionsNeeded.toTypedArray(), 101)

        }
    }


    fun isLoactionPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((context as Activity)!!.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted")
                return true
            } else {

                Log.v("", "Permission is revoked")

                ActivityCompat
                        .requestPermissions((context as Activity)!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                                45)
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted")
            return true
        }
    }

}