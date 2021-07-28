package ru.inc.myapplication.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.inc.myapplication.mvp.model.entity.GithubUser

interface IGitHubUsersCache {

    fun getUsers(): Single<List<GithubUser>>
    fun putUsers(users: List<GithubUser>): Completable
}