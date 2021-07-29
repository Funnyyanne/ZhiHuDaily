package com.anne.zhihudaily.presenter

import rx.Subscription

class MainActivityPresenter  {
    companion object {
        val LOG_TAG: String = MainActivityPresenter::class.java.simpleName
    }
    var mSubscription: Subscription? = null


}