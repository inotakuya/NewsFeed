package jp.com.inotaku.newsfeed.data

import com.squareup.moshi.Json

data class NewsResponse (
    @Json(name = "articles")
    val newsList: List<News>
)
