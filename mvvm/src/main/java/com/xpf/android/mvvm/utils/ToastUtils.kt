package com.xpf.android.mvvm.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

/**
 * Created by x-sir on 2018/7/22 :)
 * Function:Toast effect.
 */
class ToastUtils {

    companion object {

        /**
         * show short toast.
         *
         * @param message show content.
         */
        @JvmStatic
        fun showShort(context: Context?, message: String) {
            if (context == null) {
                return
            }

            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * show long toast.
         *
         * @param message show content.
         */
        @JvmStatic
        fun showLong(context: Context?, message: String) {
            if (context == null) {
                return
            }

            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
