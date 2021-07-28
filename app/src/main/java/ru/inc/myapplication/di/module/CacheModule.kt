package ru.inc.myapplication.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.inc.myapplication.mvp.model.cache.IGitHubUsersCache
import ru.inc.myapplication.mvp.model.cache.room.RoomGitHubUsersCache
import ru.inc.myapplication.mvp.model.entity.room.db.Database
import ru.inc.myapplication.ui.App

@Module
class CacheModule {

    @Provides
    fun dataBaseProvide(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Provides
    fun usersCache(db: Database): IGitHubUsersCache = RoomGitHubUsersCache(db)
}