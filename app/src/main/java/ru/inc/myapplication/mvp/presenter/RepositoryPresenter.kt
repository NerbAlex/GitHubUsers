package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.view.RepositoryView
import javax.inject.Inject

class RepositoryPresenter(val githubRepository: GithubRepository) : MvpPresenter<RepositoryView>() {

    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id ?: "")
        viewState.setTitle(githubRepository.name ?: "")
        viewState.setForksCount(githubRepository.forksCount ?: "")
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
