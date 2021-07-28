package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.geekbrains.myapplication.mvp.model.repo.IGithubRepositoriesRepo
import ru.geekbrains.myapplication.mvp.presenter.list.IRepositoryListPresenter
import ru.inc.myapplication.mvp.view.UserView
import ru.inc.myapplication.mvp.view.list.RepositoryItemView
import javax.inject.Inject

class UserPresenter(
    val uiScheduler: Scheduler,
    val repositoriesRepo: IGithubRepositoriesRepo,
    val user: GithubUser,
) : MvpPresenter<UserView>() {

    @Inject lateinit var screens: IScreens

    @Inject lateinit var router: Router

    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GithubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null
        override fun getCount() = repositories.size

        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }
    }

    val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        repositoriesListPresenter.itemClickListener = { itemView ->
            val repository = repositoriesListPresenter.repositories[itemView.pos]
            router.navigateTo(screens.repository(repository))
        }
    }

    fun loadData() {
        repositoriesRepo.getRepositories(user)
            .observeOn(uiScheduler)
            .subscribe({ repositories ->
                repositoriesListPresenter.repositories.clear()
                repositoriesListPresenter.repositories.addAll(repositories)
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
