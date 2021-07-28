package ru.inc.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.inc.myapplication.databinding.FragmentUserBinding
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.api.ApiHolder
import ru.inc.myapplication.mvp.model.cache.room.RoomGitHubRepositoriesCache
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.entity.room.db.Database
import ru.inc.myapplication.mvp.presenter.UserPresenter
import ru.inc.myapplication.mvp.view.UserView
import ru.inc.myapplication.ui.App
import ru.inc.myapplication.ui.BackButtonListener
import ru.inc.myapplication.ui.adapter.ReposotoriesRVAdapter
import ru.inc.myapplication.mvp.model.repo.RetrofitGithubRepositoriesRepo
import ru.inc.myapplication.ui.network.AndroidNetworkStatus
import java.util.logging.Logger

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    val LOG = Logger.getLogger(UserFragment::class.java.name)


    companion object {
        private const val USER_ARG = "user"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
        }
    }

    val presenter: UserPresenter by moxyPresenter {
        LOG.test("by moxyPresenter {")

        val user = arguments?.getParcelable<GithubUser>(USER_ARG) as GithubUser
        UserPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubRepositoriesRepo(
                ApiHolder.api,
                RoomGitHubRepositoriesCache(Database.getInstance()),
                AndroidNetworkStatus(requireContext())
            ),
            user,
        ).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var vb: FragmentUserBinding? = null

    var adapter: ReposotoriesRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentUserBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        LOG.test("init")

        vb?.rvRepositories?.layoutManager = LinearLayoutManager(context)
        adapter = ReposotoriesRVAdapter(presenter.repositoriesListPresenter)
        vb?.rvRepositories?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }


    override fun backPressed() = presenter.backPressed()
}