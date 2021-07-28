package ru.inc.myapplication.mvp.model.cache.room


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.inc.myapplication.mvp.model.cache.IGitHubRepositoriesCache
import ru.inc.myapplication.mvp.model.entity.GithubRepository
import ru.inc.myapplication.mvp.model.entity.GithubUser
import ru.inc.myapplication.mvp.model.entity.room.RoomGithubRepository
import ru.inc.myapplication.mvp.model.entity.room.db.Database
import java.lang.RuntimeException

class RoomGitHubRepositoriesCache(val db: Database): IGitHubRepositoriesCache {

    override fun getUsersRepos(user: GithubUser) = Single.fromCallable {
        val roomuser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No such user in cache")
        return@fromCallable db.repositoryDao.findForUser(roomuser.id).map {
            GithubRepository(it.id, it.name, it.forksCount)
        }
    }.subscribeOn(Schedulers.io())

    override fun putUserRepos(user: GithubUser, repositories: List<GithubRepository>) = Completable.fromAction {
        val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No such user in cache")
        val roomRepos = repositories.map {
            RoomGithubRepository(it.id, it.name, it.forksCount, roomUser.id)
        }
        db.repositoryDao.insert(roomRepos)
    }.subscribeOn(Schedulers.io())

}