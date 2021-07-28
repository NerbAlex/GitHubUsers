package ru.inc.myapplication.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.entity.room.RoomGithubUser
import ru.inc.myapplication.mvp.model.entity.room.db.Database
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import java.util.logging.Logger

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val db: Database,
    val networkStatus: INetworkStatus,
) : IGithubUsersRepo {

    val LOG = Logger.getLogger(RetrofitGithubUsersRepo::class.java.name)


    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            LOG.test("getUsers - isOnline")

            api.getUsers().flatMap { users ->
                Single.fromCallable {
                    val roomUsers = users.map { user ->
                        RoomGithubUser(
                            user.id,
                            user.login,
                            user.avatarUrl,
                            user.reposUrl
                        )
                    }
                    db.userDao.insert(roomUsers)
                    users
                }
            }
        } else {
            LOG.test("getUsers - isOffline")

            Single.fromCallable {
                db.userDao.getAll().map { roomUser ->
                    GithubUser(
                        roomUser.id,
                        roomUser.login,
                        roomUser.avatarUrl,
                        roomUser.reposUrl
                    )
                }
            }
        }
    }.subscribeOn(Schedulers.io())
}