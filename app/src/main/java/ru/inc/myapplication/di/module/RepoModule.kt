package ru.inc.myapplication.di.module

import dagger.Module
import dagger.Provides
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import ru.inc.myapplication.mvp.model.repo.IGithubUsersRepo
import ru.inc.myapplication.mvp.model.repo.RetrofitGithubUsersRepo
import ru.inc.myapplication.ui.network.AndroidNetworkStatus

@Module
class RepoModule {

//    @Provides
//    fun usersRepoProvide(api: IDataSource, networkStatus: INetworkStatus): IGithubUsersRepo = RetrofitGithubUsersRepo()

}