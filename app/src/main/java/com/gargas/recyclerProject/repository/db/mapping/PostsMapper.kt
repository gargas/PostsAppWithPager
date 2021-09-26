package com.gargas.recyclerProject.repository.db.mapping

import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.network.response.GetPostsResponse

class PostsMapper {

    fun transform(response: GetPostsResponse): Posts {
        return Posts(
            total = response.meta!!.pagination.total,
            page = response.meta!!.pagination.page,
            posts = response.data!!.map {
                Posts.Data(
                    0,
                    it!!.body,
                    it.id,
                    it.title,
                    it.userId
                )
            }
        )
    }
}