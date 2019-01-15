package be.appwise.simplifiedPokedex

import android.annotation.SuppressLint
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk

class MyApplication : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this

        // Initialize Hawk
        Hawk.init(this).build()
    }
}