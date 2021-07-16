package com.anne.zhihudaily.presenter

import android.util.Log
import com.anne.zhihudaily.adapter.DailyInfoAdapter
import com.anne.zhihudaily.model.LatestInfoGson
import com.anne.zhihudaily.service.DailyInfoService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Scheduler
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DailyInfoFragmentPresenter {
    companion object {
        val LOG_TAG: String = DailyInfoFragmentPresenter::class.java.simpleName
    }

    var mSubscription: Subscription? = null
    fun getLatestInfo(adapter: DailyInfoAdapter) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(DailyInfoService.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: DailyInfoService = retrofit.create(DailyInfoService::class.java)

        mSubscription = service.getLastInfo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<LatestInfoGson>() {
                override fun onCompleted() {
                    Log.d(LOG_TAG, "getLatestInfo onCompleted.")

                }

                override fun onError(e: Throwable?) {
                    Log.d(LOG_TAG, "getLatestInfo onError.")
                    e?.printStackTrace()
                }

                override fun onNext(t: LatestInfoGson?) {
                    Log.d(LOG_TAG, "getLatestInfo onNext.")
                    val stories: ArrayList<DailyInfoAdapter.StoryWrapper> = ArrayList()
                    stories.add(
                        DailyInfoAdapter.StoryWrapper(
                            DailyInfoAdapter.TYPE_TOP_STORIES,
                            "",
                            "",
                            "",
                            "首页"
                        )
                    )
                    t?.let {
                        t.stories?.let {
                            t.stories.map {
//                                stories.add(DailyInfoAdapter.StoryWrapper(DailyInfoAdapter.TYPE_CATEGORY,it.images[0],it.id.toString(),it.title,"今日热闻"))
                            }
                            adapter.add(t.top_stories, stories)
                        }
                    }
                }
            })
    }

    fun getBeforeInfo() {}
    fun unsubscribe() {
        if (mSubscription?.isUnsubscribed ?: false) {
            mSubscription?.unsubscribe()
        }
    }
}