package jp.com.inotaku.newsfeed.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.com.inotaku.R
import jp.com.inotaku.newsfeed.data.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

class NewsListAdapter(private val activity: Activity) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    var newsList: List<News> = emptyList()

    class ViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
        LayoutContainer {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // レイアウトの設定
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedNews = newsList[position]

        holder.run {
            txtNewsTitle.text = selectedNews.title
            txtPublishDate.text = selectedNews.publishData
            txtDesc.text = selectedNews.description
        }
    }
}