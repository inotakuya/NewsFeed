package jp.com.inotaku.newsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.com.inotaku.R
import jp.com.inotaku.newsfeed.ui.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.flagment_container, MainFragment())
            .commit()
    }
}