package tech.appzilla.com.setmydate.Utils

import android.content.Context
import android.widget.Toast



class ToastUtils private constructor() {

    init {
        throw Error("U will not able to instantiate it")
    }



    companion object {
        /**
         * @param context
         * @param message
         * @return
         */
        fun showToastShort(context: Context?, message: String) {
            if (!StringUtils.isBlank(message) && !message.equals("", ignoreCase = true))
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * @param context
         * @param message
         * @return
         */
        fun showToastLong(context: Context?, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

    }

}
