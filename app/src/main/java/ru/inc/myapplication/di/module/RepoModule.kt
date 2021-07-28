package ru.inc.myapplication.di.module

import dagger.Module
import dagger.Provides
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.cache.IGitHubUsersCache
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import ru.inc.myapplication.mvp.model.repo.IGithubUsersRepo
import ru.inc.myapplication.mvp.model.repo.RetrofitGithubUsersRepo

@Module
class RepoModule {

    @Provides
    fun usersRepoProvideProvide(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGitHubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, cache, networkStatus)

}