package ru.inc.myapplication.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.view.RepositoryView

class RepositoryPresenter(val router: Router, val githubRepository: GithubRepository) : MvpPresenter<RepositoryView>() {

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
