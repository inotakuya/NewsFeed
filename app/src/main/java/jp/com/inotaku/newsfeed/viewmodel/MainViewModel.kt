package jp.com.inotaku.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.com.inotaku.newsfeed.NewsRepository
import jp.com.inotaku.newsfeed.data.News
import jp.com.inotaku.newsfeed.utils.LoadStatus
import kotlinx.coroutines.launch

/**
 * メインViewModel
 */
class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    // ニュースリスト
    var newsList = MutableLiveData<List<News>>()

    // ニュースリポジトリ
    private val repository: NewsRepository by lazy {
        NewsRepository(app)
    }

    // ロードステータス
    fun getState(): MutableLiveData<LoadStatus> = repository.loadStatus

    /**
     * ニュースリストを取得
     * @param searchWord 検索文字
     */
    fun getNewsList(searchWord: String) {
        viewModelScope.launch {
            newsList.value = repository.getNews(searchWord)
        }
    }

}