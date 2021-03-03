package com.keeneye.flickr.app

import android.app.Application

class App: Application() {
    companion object {
        // application instance
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Application global instance
        instance = this
    }
}