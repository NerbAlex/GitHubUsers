package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.inc.myapplication.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.myapplication.mvp.presenter.list.IUserListPresenter
import ru.inc.myapplication.mvp.view.UsersView
import ru.inc.myapplication.mvp.view.list.UserItemView
import java.util.logging.Logger
import javax.inject.Inject

class UsersPresenter(
    val uiScheduler: Scheduler,
) : MvpPresenter<UsersView>() {

    @Inject lateinit var screens: IScreens
    @Inject lateinit var router: Router
    @Inject lateinit var usersRepo: IGithubUsersRepo

    val LOG = Logger.getLogger(UsersPresenter::class.java.name)

    class UsersListPresenter : IUserListPresenter {

        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        LOG.test("onFirstViewAttach")

        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(screens.user(user))
        }
    }

    fun loadData() {
        LOG.test("loadData")

        usersRepo.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                LOG.test(".subscribe(")

                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
