package jp.com.inotaku.newsfeed

import android.content.Context
import androidx.lifecycle.MutableLiveData
import jp.com.inotaku.newsfeed.api.NewsApiService
import jp.com.inotaku.newsfeed.data.News
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

    /**
     * APIでニュース情報を取得して返す
     * @param searchWord 検索文字
     */
    suspend fun getNews(searchWord: String): List<News> {
        loadStatus.value = LoadStatus.LOADING
        var newsList = emptyList<News>()
        runCatching {
            newsApiService.getNews(searchWord)
        }.onSuccess { response ->
            if (response.isSuccessful) {
                // 成功の場合
                loadStatus.value = LoadStatus.DONE
                newsList = response.body()!!.newsList
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
        return newsList
    }
}