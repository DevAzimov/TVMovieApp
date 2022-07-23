package com.magicapp.android_imperative.utils

import android.util.Log
import com.magicapp.android_imperative.network.Server
import com.magicapp.android_imperative.network.Server.IS_TESTER

class Logger {
    companion object{
        fun d(tag: String, msg: String) {
            if (Server.IS_TESTER) Log.d(tag, msg)
        }

        fun i(tag: String, msg: String) {
            if (Server.IS_TESTER) Log.i(tag, msg)
        }

        fun v(tag: String, msg: String) {
            if (Server.IS_TESTER) Log.v(tag, msg)
        }

        fun e(tag: String, msg: String) {
            if (Server.IS_TESTER) Log.e(tag, msg)
        }

    }
}