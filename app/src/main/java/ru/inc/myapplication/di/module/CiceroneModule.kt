package ru.inc.myapplication.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.inc.myapplication.ui.navigation.AndroidScreens
import javax.inject.Singleton

@Module
class CiceroneModule {

    var cicerone: Cicerone<Router> = Cicerone.create()

//    @Provides
//    fun ciceroneProvide(): Cicerone<Router> = cicerone

//    @Singleton
    @Provides
    fun navigatorHolderProvide(): NavigatorHolder = cicerone.getNavigatorHolder()

//    @Singleton
    @Provides
    fun routerProvide(): Router = cicerone.router

    @Singleton
    @Provides
    fun screensProvide(): IScreens = AndroidScreens()

}