package jp.com.inotaku.newsfeed.data

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "news_table")
data class News(
    @PrimaryKey
    @NonNull
    val url: String,
    val title: String?,
    @Json(name = "publishedAt")
    @ColumnInfo(name = "publish_date")
    val publishDate: String?,
    val description: String?,
    @Json(name = "urlToImage")
    @ColumnInfo(name = "image_uri")
    @Nullable
    var imageUri: String?
)
