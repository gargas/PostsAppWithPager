package com.gargas.recyclerProject.repository.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gargas.recyclerProject.models.Posts

@Dao
interface PostRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Posts.Data>)

    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Posts.Data>

    @Query("DELETE FROM posts")
    fun clearPosts()

}

@Dao
interface PostRemoteKeysRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Posts.PostsRemoteKeys>)

    @Query("SELECT * FROM post_remote_keys WHERE id = :id")
    fun remoteKeysByPostId(id: Long): Posts.PostsRemoteKeys?

    @Query("DELETE FROM post_remote_keys")
    fun clearRemoteKeys()

}