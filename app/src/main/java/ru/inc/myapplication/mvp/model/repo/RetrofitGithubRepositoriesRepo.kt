package ru.inc.myapplication.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.myapplication.mvp.model.repo.IGithubRepositoriesRepo
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.cache.IGitHubRepositoriesCache
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.network.INetworkStatus

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val cache: IGitHubRepositoriesCache,
    val networkStatus: INetworkStatus
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getRepositories(user.reposUrl)
                    .flatMap { repositories ->
                        cache.putUserRepos(user, repositories).toSingleDefault(repositories)
                }
            } else {
                cache.getUsersRepos(user)
            }
        }.subscribeOn(Schedulers.io())
}