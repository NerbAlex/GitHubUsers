package ru.inc.myapplication.mvp.model.navigation

import com.github.terrakok.cicerone.Screen
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.model.entity.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(githubUser: GithubUser): Screen
    fun repository(githubRepository: GithubRepository): Screen
}