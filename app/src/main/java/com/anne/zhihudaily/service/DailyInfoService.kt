package com.anne.zhihudaily.service

import com.anne.zhihudaily.model.LatestInfoGson
import com.anne.zhihudaily.model.LatestInfoJson
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface DailyInfoService {
    companion object{
        const val BASE_URL = "http://news-at.zhihu.com/api/4/"
    }
    @GET("news/latest") fun getLastInfo(): Observable<LatestInfoGson>
    @GET("news/before/{date}") fun getBeforeInfo(@Path("date") date:String): Observable<LatestInfoGson>
}