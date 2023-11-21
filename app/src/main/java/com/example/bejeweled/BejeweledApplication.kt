package com.example.bejeweled

import android.app.Application
import com.example.bejeweled.data.AppContainer
import com.example.bejeweled.data.AppDataContainer

class BejeweledApplication: Application(){

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}