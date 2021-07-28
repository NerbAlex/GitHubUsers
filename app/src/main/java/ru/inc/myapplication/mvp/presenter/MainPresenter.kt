package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.inc.myapplication.mvp.view.MainView

class MainPresenter(val router: Router, val screens: IScreens) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}
