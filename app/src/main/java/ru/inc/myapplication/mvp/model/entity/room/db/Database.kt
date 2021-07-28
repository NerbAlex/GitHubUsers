package ru.inc.myapplication.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.inc.myapplication.mvp.model.entity.room.RoomGithubRepository
import ru.inc.myapplication.mvp.model.entity.room.RoomGithubUser
import ru.inc.myapplication.mvp.model.entity.room.dao.RepositoryDao
import ru.inc.myapplication.mvp.model.entity.room.dao.UserDao
import java.lang.RuntimeException


@androidx.room.Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao

    companion object {

        const val DB_NAME = "database.db"
        private var instance: Database? = null

        fun getInstance() = instance ?: throw RuntimeException("Create database with create()")

        fun create(context: Context) {
            if(instance == null){
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME)
                    .build()
            }
        }
    }
}