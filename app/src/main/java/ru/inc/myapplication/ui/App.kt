package ru.inc.myapplication.ui

import android.app.Application
import ru.inc.myapplication.di.component.AppComponent
import ru.inc.myapplication.di.component.DaggerAppComponent
import ru.inc.myapplication.mvp.model.entity.room.db.Database

class App : Application() {
    companion object {
        @get: Synchronized
        lateinit var instance: App
            private set
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)

        appComponent = DaggerAppComponent.builder().build()
    }
}
