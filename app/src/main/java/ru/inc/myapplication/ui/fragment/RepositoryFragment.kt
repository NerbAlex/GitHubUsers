package ru.inc.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.inc.myapplication.databinding.FragmentRepositoryBinding
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.presenter.RepositoryPresenter
import ru.inc.myapplication.mvp.view.RepositoryView
import ru.inc.myapplication.ui.App
import ru.inc.myapplication.ui.BackButtonListener


class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    companion object {
        private const val REPOSITORY_ARG = "repository"

        fun newInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }
        }
    }

    private var vb: FragmentRepositoryBinding? = null

    val presenter: RepositoryPresenter by moxyPresenter {
        val repository = arguments?.getParcelable<GithubRepository>(REPOSITORY_ARG) as GithubRepository
        RepositoryPresenter(App.instance.router, repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRepositoryBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {}

    override fun setId(text: String) {
       vb?.tvId?.text = text
    }

    override fun setTitle(text: String) {
        vb?.tvTitle?.text = text
    }

    override fun setForksCount(text: String) {
        vb?.tvForksCount?.text = text
    }

    override fun backPressed() = presenter.backPressed()
}