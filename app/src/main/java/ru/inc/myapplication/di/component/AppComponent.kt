package ru.inc.myapplication.di.component

import dagger.Component
import ru.inc.myapplication.di.module.*
import ru.inc.myapplication.mvp.presenter.MainPresenter
import ru.inc.myapplication.mvp.presenter.RepositoryPresenter
import ru.inc.myapplication.mvp.presenter.UserPresenter
import ru.inc.myapplication.mvp.presenter.UsersPresenter
import ru.inc.myapplication.ui.activity.MainActivity


@Component(
    modules = [
        CiceroneModule::class,
        RepoModule::class,
        ApiModule::class,
        AppModule::class,
        CacheModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
    fun inject(userPresenter: UserPresenter)


}