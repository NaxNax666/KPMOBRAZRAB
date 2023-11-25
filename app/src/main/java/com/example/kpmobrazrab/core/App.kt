package com.example.kpmobrazrab.core

import android.app.Application
import android.graphics.BitmapFactory
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.R

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        Storage.bitmap = BitmapFactory.decodeResource(resources, R.drawable.img1)
    }
}