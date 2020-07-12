package jp.com.inotaku.newsfeed.api

import jp.com.inotaku.newsfeed.const.Constants.Companion.API_KEY
import jp.com.inotaku.newsfeed.const.Constants.Companion.PAGESIZE
import jp.com.inotaku.newsfeed.data.NewsResponse
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.com.inotaku.newsfeed.const.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface NewsApiService {

    @GET("everything")
    fun getNews(
        @Query("q") searchString: String,
        @Query("apikey") apiKey: String = API_KEY,
        @Query("pageSize") pageSize: Int = PAGESIZE
    ): Call<NewsResponse>

    companion object {
        /**
         * Moshiの設定とRetrofitの設定をしてNewsApiServiceを作成する
         */
        fun createApiService(): NewsApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NewsApiService::class.java)
        }

    }
}