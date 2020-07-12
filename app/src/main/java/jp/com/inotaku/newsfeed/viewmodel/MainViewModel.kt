package jp.com.inotaku.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.*
import jp.com.inotaku.newsfeed.NewsRepository
import jp.com.inotaku.newsfeed.data.News
import jp.com.inotaku.newsfeed.utils.LoadStatus
import kotlinx.coroutines.launch

/**
 * メインViewModel
 */
class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    // ニュースリポジトリ
    private val repository: NewsRepository by lazy {
        NewsRepository(app)
    }

    // 検索文字
    val searchWord = MutableLiveData<String>()

    // ロードステータス
    fun getState(): MutableLiveData<LoadStatus> = repository.loadStatus

    /**
     * 検索文字を設定
     * @param word 検索文字
     */
    fun setSearchWord(word: String) {
        searchWord.value = word
    }

    /**
     * ニュースリストを取得
     * @param searchWord 検索文字
     */
    val newsList: LiveData<List<News>> = Transformations.switchMap(searchWord) { word ->
        word?.let {
            liveData {
                repository.getNews(word)
                emitSource(repository.searchNews(word))
            }
        }
    }

}