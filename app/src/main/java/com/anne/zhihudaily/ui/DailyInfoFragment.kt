package com.anne.zhihudaily.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anne.zhihudaily.R
import com.anne.zhihudaily.adapter.DailyInfoAdapter
import com.anne.zhihudaily.presenter.DailyInfoFragmentPresenter

class DailyInfoFragment : Fragment() {
    private var mParesenter: DailyInfoFragmentPresenter = DailyInfoFragmentPresenter()
    private var mAdpater: DailyInfoAdapter? = null
    private var mScrollCallback: ScrollCallback? = null

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater!!.inflate(R.layout.fragment_dailyinfo, container, false)
        if (root is SwipeRefreshLayout) {
            root.isEnabled = false
        }
        val recycleView: RecyclerView = root.findViewById(R.id.info_list) as RecyclerView
        val linerLayoutManager = LinearLayoutManager(context)
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val des: String? =
                    mAdpater?.getDescription(linerLayoutManager.findFirstCompletelyVisibleItemPosition())
                if (!TextUtils.isEmpty(des)) {
                    mScrollCallback?.onTitleChanged(des!!)
                } else {
                    mScrollCallback?.onTitleChanged("首页")
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        mAdpater = DailyInfoAdapter(object : DailyInfoAdapter.FragmentCallBack {
            override fun toActivity(id: String) {
                this@DailyInfoFragment.toActivity(id)
            }
        })
        recycleView.adapter = mAdpater
        mParesenter.getLatestInfo(mAdpater!!)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface ScrollCallback {
        fun onTitleChanged(text: String)
    }

    fun setScrollCallback(callback: ScrollCallback) {
        mScrollCallback = callback
    }

    fun toActivity(id: String) {
        val intent: Intent = Intent(activity, DetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putString("id", id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    companion object {
        val TYPE_DAILYINFO = 1
        val TYPE_THEMEINFO = 2
        val FRAGMENT_TYPE = "fragment_type"

        fun newInstance():DailyInfoFragment {
            return DailyInfoFragment()
        }
    }
}