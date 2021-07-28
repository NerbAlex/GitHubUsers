package ru.inc.myapplication.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.inc.myapplication.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}