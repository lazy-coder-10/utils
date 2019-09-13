package tech.appzilla.com.setmydate.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import tech.appzilla.com.setmydate.R

import java.util.*

object ProgressBarUtils {

    var dialogBuilder: Dialog ?  = null

    fun ShowProgressDialog(context: Context) {

        if(dialogBuilder != null)
                dialogBuilder!!.dismiss()

        dialogBuilder = Dialog(context, R.style.MaterialSearch)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
        dialogBuilder!!.setContentView(dialogView)
        dialogBuilder!!.setCancelable(false)
        dialogBuilder!!.setCanceledOnTouchOutside(false)
        dialogBuilder!!.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        try {
            dialogBuilder!!.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    fun HideProgressDialog() {

        try {

            if(dialogBuilder != null)
                dialogBuilder!!.dismiss()

        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()

        } catch (e: Exception) {

            e.printStackTrace()
        }



    }

    fun isLoading(): Boolean {

        return dialogBuilder != null && dialogBuilder!!.isShowing
    }


}
