package com.anne.zhihudaily.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anne.zhihudaily.R
import com.anne.zhihudaily.adapter.ThemeInfoAdapter
import com.anne.zhihudaily.presenter.ThemeInfoPresenter

/**
 * Created by Lucifer on 2017/6/17.
 */
class ThemeInfoFragment: Fragment() {

    var themeId: Int = 0
    private lateinit var mPresenter: ThemeInfoPresenter
    private lateinit var mAdapter: ThemeInfoAdapter

    companion object {
        val LOG_TAG: String = ThemeInfoFragment::class.java.simpleName
        val THEME_ID = "themeId"
        val DEFAULT_THEMEID = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(LOG_TAG, "onCreateView")
        val root = inflater!!.inflate(R.layout.fragment_themeinfo, container, false)
        val infoList = root.findViewById(R.id.info_list) as RecyclerView
        infoList.layoutManager = LinearLayoutManager(context)
        mAdapter = ThemeInfoAdapter()
        mAdapter.setClickCallback(object: ThemeInfoAdapter.ClickCallback {
            override fun clickEditor() {
            }

            override fun clickThemeInfo(infoId: Int) {
                toActivity(infoId.toString())
            }
        })
        infoList.adapter = mAdapter
        initPresenter()
        return root
    }

    // 第一次创建 Fragment 的时候，通过 bundle 传值确保 themeId 能够被收到并触发 updateThemeInfo function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        themeId = bundle!!.getInt(THEME_ID, DEFAULT_THEMEID)
        if (themeId != DEFAULT_THEMEID)
            updateThemeInfo()
    }

    fun updateThemeInfo() {
        Log.d(LOG_TAG, "updateThemeInfo themeId is $themeId.")
        if (mPresenter.isLoading())
            mPresenter.unsubscribe()
        mPresenter.getThemeDetail(themeId, mAdapter)
    }

    private fun initPresenter() {
        mPresenter = ThemeInfoPresenter()
    }

    fun toActivity(id: String) {
        val intent: Intent = Intent(activity, DetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putString("id", id)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}