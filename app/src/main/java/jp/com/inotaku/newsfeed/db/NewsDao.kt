package jp.com.inotaku.newsfeed.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import jp.com.inotaku.newsfeed.data.News

/**
 * DAO
 */
@Dao
interface NewsDao {

    /**
     * 一括登録
     * @param newsList ニュースリスト
     */
    @Insert
    suspend fun insert(newsList: List<News>)

    /**
     * タイトルと詳細を検索文字で検索
     * @param searchWord 検索文字
     */
    @Query("select * from news_table where title like :searchWord or description like :searchWord")
    fun searchNews(searchWord: String): LiveData<List<News>>

    /**
     * 削除
     * @param news ニュース情報
     */
    @Delete
    suspend fun delete(news: News)
}
