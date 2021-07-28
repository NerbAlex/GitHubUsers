package ru.inc.myapplication.ui.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.navigation.IScreens
import ru.inc.myapplication.ui.fragment.RepositoryFragment
import ru.inc.myapplication.ui.fragment.UserFragment
import ru.inc.myapplication.ui.fragment.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(githubUser: GithubUser) = FragmentScreen { UserFragment.newInstance(githubUser) }
    override fun repository(githubRepository: GithubRepository) = FragmentScreen { RepositoryFragment.newInstance(githubRepository) }
}