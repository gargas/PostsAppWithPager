package com.gargas.recyclerProject.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gargas.recyclerProject.network.response.GetPostsResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Posts(
    val total: Int? = 0,
    val page: Int? = 0,
    val posts: List<Data>
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Parcelize
    @Entity(tableName = "posts")
    data class Data(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        var body: String?,
        var postId: Int?,
        var title: String?,
        @SerializedName("user_id")
        var userId: Int?
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "post_remote_keys")
    data class PostsRemoteKeys(
        @PrimaryKey val id: Long,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable
}