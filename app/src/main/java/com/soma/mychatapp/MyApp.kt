package com.soma.mychatapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.soma.mychatapp.di.appModule
import com.soma.mychatapp.util.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}