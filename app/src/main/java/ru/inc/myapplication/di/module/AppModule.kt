package ru.inc.myapplication.di.module

import dagger.Module
import dagger.Provides
import ru.inc.myapplication.ui.App

@Module
class AppModule(val app: App) {

    @Provides
    fun appProvide(): App = app
}