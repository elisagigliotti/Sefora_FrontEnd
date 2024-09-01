package it.unical.informatica.ea.sefora_frontend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SeforaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}