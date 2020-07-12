package jp.com.inotaku.newsfeed.utils

import android.content.Context
import android.widget.Toast

/**
 * トーストを表示する
 * @param context コンテキスト
 * @param message 表示するメッセージ
 */
fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}