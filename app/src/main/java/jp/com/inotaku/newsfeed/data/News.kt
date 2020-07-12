package jp.com.inotaku.newsfeed.data

import androidx.annotation.Nullable
import com.squareup.moshi.Json

data class News(
    val title: String?,
    @Json(name = "publishedAt")
    val publishDate: String?,
    val url: String?,
    val description: String?,
    @Json(name = "urlToImage")
    @Nullable
    var imageUri: String?
)
