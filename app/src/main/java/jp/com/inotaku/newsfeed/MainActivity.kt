package jp.com.inotaku.newsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.com.inotaku.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}