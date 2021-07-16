package com.anne.zhihudaily

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Fresco.initialize(applicationContext)
    }

}