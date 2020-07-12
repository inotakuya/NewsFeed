package jp.com.inotaku.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.com.inotaku.newsfeed.api.NewsApiService
import jp.com.inotaku.newsfeed.data.News
import jp.com.inotaku.newsfeed.data.NewsResponse
import jp.com.inotaku.newsfeed.utils.makeToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    // ニュースリスト
    var newsList = MutableLiveData<List<News>>()

    // apiサービス
    private val newsApiService: NewsApiService by lazy {
        NewsApiService.createApiService()
    }

    /**
     * ニュースリストを取得
     * @param searchWord 検索文字
     */
    fun getNewsList(searchWord: String) {
        try {
            newsApiService.getNews(searchWord)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        makeToast(app, t.message!!)
                    }

                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        newsList.value = response.body()?.newsList
                    }
                })

        } catch (e: Exception) {
            makeToast(app, "Exception: ${e.message}")
            e.printStackTrace()
        }
    }

}