package ru.inc.myapplication.ui.activity

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.inc.myapplication.R
import ru.inc.myapplication.databinding.ActivityMainBinding

import ru.inc.myapplication.mvp.presenter.MainPresenter
import ru.inc.myapplication.mvp.view.MainView
import ru.inc.myapplication.ui.App
import ru.inc.myapplication.ui.BackButtonListener
import ru.inc.myapplication.ui.navigation.AndroidScreens
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject lateinit var navigatorHolder: NavigatorHolder

    private var vb: ActivityMainBinding? = null
    private val navigator = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}
