package jp.com.inotaku.newsfeed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jp.com.inotaku.newsfeed.api.NewsApiService
import jp.com.inotaku.newsfeed.data.News
import jp.com.inotaku.newsfeed.db.NewsDao
import jp.com.inotaku.newsfeed.db.NewsDatabase
import jp.com.inotaku.newsfeed.utils.LoadStatus
import jp.com.inotaku.newsfeed.utils.makeToast

/**
 * ニュースリポジトリ
 */
class NewsRepository(private val context: Context) {

    val loadStatus = MutableLiveData<LoadStatus>()

    // apiサービス
    private val newsApiService: NewsApiService by lazy {
        NewsApiService.createApiService()
    }

    // DAO
    private val newsDao: NewsDao by lazy {
        NewsDatabase.getDatabase(context).newsDao()
    }

    /**
     * APIでニュース情報を取得して返す
     * @param searchWord 検索文字
     */
    suspend fun getNews(searchWord: String) {
        loadStatus.value = LoadStatus.LOADING
        runCatching {
            newsApiService.getNews(searchWord)
        }.onSuccess { response ->
            if (response.isSuccessful) {
                // 成功の場合
                loadStatus.value = LoadStatus.DONE
                response.body()?.newsList?.forEach {
                    // 一度削除(一括登録するときにPK重複で落ちるため)
                    newsDao.delete(it)
                }
                // 一括で登録
                newsDao.insert(response.body()!!.newsList)
            } else {
                // 失敗した場合、トーストでメッセージ表示する
                loadStatus.value = LoadStatus.ERROR
                makeToast(context, response.message())
            }
        }.onFailure { e ->
            // 失敗した場合、トーストでメッセージ表示する
            loadStatus.value = LoadStatus.ERROR
            makeToast(context, "Exception: ${e.message}")
        }
    }

    /**
     * DBからニュース情報を検索する
     * @param word 検索文字
     */
    fun searchNews(word: String): LiveData<List<News>> {
        return newsDao.searchNews("%${word}%")
    }
}