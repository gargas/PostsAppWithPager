package com.gargas.recyclerProject.repository.db.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gargas.recyclerProject.models.Posts

@Database(
    entities = [Posts.Data::class, Posts.PostsRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postsRxDao(): PostRxDao
    abstract fun postRemoteKeysRxDao(): PostRemoteKeysRxDao

    companion object {
        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context): PostDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PostDatabase::class.java, "POSTS.db")
                .build()
    }
}