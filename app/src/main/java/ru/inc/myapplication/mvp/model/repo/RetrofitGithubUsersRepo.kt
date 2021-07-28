package ru.inc.myapplication.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.cache.IGitHubUsersCache
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import java.util.logging.Logger

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val cache: IGitHubUsersCache,
    val networkStatus: INetworkStatus,
) : IGithubUsersRepo {

    val LOG = Logger.getLogger(RetrofitGithubUsersRepo::class.java.name)

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            LOG.test("getUsers - isOnline")

            api.getUsers()
                .flatMap { users ->
                    cache.putUsers(users).toSingleDefault(users)
                }
        } else {
            LOG.test("getUsers - isOffline")

            cache.getUsers()
        }
    }.subscribeOn(Schedulers.io())
}
