package com.nubari.montra

import android.app.Application
import com.nubari.montra.general.util.Preferences
import dagger.hilt.android.HiltAndroidApp

val preferences: Preferences by lazy {
    MontraApplication.preferences!!
}

@HiltAndroidApp
class MontraApplication : Application() {
    companion object {
        var preferences: Preferences? = null
        lateinit var instance: MontraApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        preferences = Preferences(applicationContext)
    }
}