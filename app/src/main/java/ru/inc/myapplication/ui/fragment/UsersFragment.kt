package ru.inc.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.inc.myapplication.databinding.FragmentUsersBinding
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.api.ApiHolder
import ru.inc.myapplication.mvp.model.cache.room.RoomGitHubUsersCache
import ru.inc.myapplication.mvp.model.entity.room.db.Database
import ru.inc.myapplication.mvp.model.repo.RetrofitGithubUsersRepo
import ru.inc.myapplication.mvp.presenter.UsersPresenter
import ru.inc.myapplication.mvp.view.UsersView
import ru.inc.myapplication.ui.App
import ru.inc.myapplication.ui.BackButtonListener
import ru.inc.myapplication.ui.adapter.UsersRVAdapter
import ru.inc.myapplication.ui.image.GlideImageLoader
import ru.inc.myapplication.ui.network.AndroidNetworkStatus
import java.util.logging.Logger

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    val LOG = Logger.getLogger(UsersFragment::class.java.name)

    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        LOG.test("by moxyPresenter {")
        UsersPresenter(AndroidSchedulers.mainThread())
            .apply {
                App.instance.appComponent.inject(this)
            }
    }

    var adapter: UsersRVAdapter? = null

    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        LOG.test("init")

        vb?.rvUsers?.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        LOG.test("updateList")

        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}