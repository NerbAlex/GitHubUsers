package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.inc.myapplication.mvp.view.MainView
import javax.inject.Inject

class MainPresenter() : MvpPresenter<MainView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}
