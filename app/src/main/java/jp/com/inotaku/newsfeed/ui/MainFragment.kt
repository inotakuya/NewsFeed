package jp.com.inotaku.newsfeed.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jp.com.inotaku.R
import jp.com.inotaku.newsfeed.adapter.NewsListAdapter
import jp.com.inotaku.newsfeed.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * メインの画面
 */
class MainFragment : Fragment() {

    // ViewModel
    private val viewModel: MainViewModel by viewModels()

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
            // キーボードを非表示にする
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            // ニュースリストを取得
            viewModel.getNewsList(inputSearchWord.text.toString())
            observerViewModel()

        }
    }

    /**
     * observerの設定
     */
    private fun observerViewModel() {
        viewModel.newsList.observe(requireActivity(), Observer { newsList ->
            // 検索ワードの表示を設定
            txtSearchWord.text = inputSearchWord.text.toString()

            // アダプターにリストを設定
            newsList?.let {
                newsListAdapter.newsList = it
            }
            // リストを更新
            newsListAdapter.notifyDataSetChanged()
        })
    }

}