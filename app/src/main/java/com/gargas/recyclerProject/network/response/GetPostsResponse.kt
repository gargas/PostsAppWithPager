package com.gargas.recyclerProject.network.response


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class GetPostsResponse(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("meta")
    var meta: Meta?
) : Parcelable {
    @Parcelize
     data class Data(
        @SerializedName("body")
        var body: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("user_id")
        var userId: Int?
    ) : Parcelable

    @Parcelize
     data class Meta(
        @SerializedName("pagination")
        var pagination: Pagination
    ) : Parcelable {
        @Parcelize
         data class Pagination(
            @SerializedName("limit")
            var limit: Int,
            @SerializedName("links")
            var links: Links,
            @SerializedName("page")
            var page: Int,
            @SerializedName("pages")
            var pages: Int,
            @SerializedName("total")
            var total: Int
        ) : Parcelable {
            @Parcelize
             data class Links(
                @SerializedName("current")
                var current: String?,
                @SerializedName("next")
                var next: String?,
                @SerializedName("previous")
                var previous: String?
            ) : Parcelable
        }
    }
}