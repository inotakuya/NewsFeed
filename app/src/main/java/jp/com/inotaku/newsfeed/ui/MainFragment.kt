package jp.com.inotaku.newsfeed.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import jp.com.inotaku.R
import jp.com.inotaku.newsfeed.adapter.NewsListAdapter
import jp.com.inotaku.newsfeed.api.NewsApiService
import jp.com.inotaku.newsfeed.data.NewsResponse
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * メインの画面
 */
class MainFragment : Fragment() {

    // apiサービス
    private val newsApiService: NewsApiService by lazy {
        NewsApiService.createApiService()
    }

    // アダプター
    private val newsListAdapter: NewsListAdapter by lazy {
        NewsListAdapter(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // アダプターの設定
        newsList.run {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = newsListAdapter
        }

        searchButton.setOnClickListener {
            try {
                newsApiService.getNews(inputSearchWord.text.toString())
                    .enqueue(object : Callback<NewsResponse> {
                        override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                            println(t)
                        }

                        override fun onResponse(
                            call: Call<NewsResponse>,
                            response: Response<NewsResponse>
                        ) {
                            // 検索ワードの表示を設定
                            txtSearchWord.text = inputSearchWord.text.toString()
                            val newsResponse = response.body()
                            // アダプターにリストを設定
                            newsResponse?.let {
                                newsListAdapter.newsList = it.newsList
                            }
                            // リストを更新
                            newsListAdapter.notifyDataSetChanged()
                        }
                    })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}