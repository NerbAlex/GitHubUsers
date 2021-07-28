package ru.geekbrains.myapplication.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.model.entity.GithubUser

interface IGithubRepositoriesRepo {
    fun getRepositories(user: GithubUser): Single<List<GithubRepository>>
}