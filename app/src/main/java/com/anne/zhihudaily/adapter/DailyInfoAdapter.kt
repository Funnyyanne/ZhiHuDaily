package com.anne.zhihudaily.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.anne.zhihudaily.R
import com.anne.zhihudaily.model.LatestInfoGson
import com.anne.zhihudaily.view.IndicatorView
import com.facebook.drawee.view.SimpleDraweeView

class DailyInfoAdapter(val callback: FragmentCallBack): RecyclerView.Adapter<DailyInfoAdapter.BaseViewHolder>() {

    private var mInfo: ArrayList<StoryWrapper> = ArrayList()
    private var mTopStories: ArrayList<LatestInfoGson.TopStoriesBean> = ArrayList()
    private val mStoryOnClickListener: View.OnClickListener = View.OnClickListener {

    }

    companion object {
        val LOG_TAG:String = DailyInfoAdapter::class.java.simpleName
        val TYPE_TOP_STORIES = -1
        val TYPE_STORIES = -2
        val TYPE_CATEGORY = -3
    }

    override fun getItemViewType(position: Int): Int {
        return mInfo[position].type
    }

    inner open class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {

        }
        open fun bindView(position: Int){}

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        Log.d(LOG_TAG,"onCreateViewHolder viewTYPe: ${viewType}")
        return when (viewType) {
            TYPE_TOP_STORIES -> return TopStoriesViewHolder(
                LayoutInflater.from(parent?.context).inflate(R.layout.info_item_topstories, parent, false)
            )
//            TYPE_TOP_STORIES -> return TopStoriesViewHolder(
//                LayoutInflater.from(parent?.context).inflate(R.layout.activity_main, parent, false)
//            )
            else -> BaseViewHolder(View(parent?.context))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        TODO("Not yet implemented")
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
       return mInfo.size
    }
    fun add(topStories: List<LatestInfoGson.TopStoriesBean>, info:List<StoryWrapper>){
        mTopStories.clear()
        mTopStories.addAll(topStories)

        mInfo.clear()
        mInfo.addAll(info)
        notifyDataSetChanged()
    }

    inner  class TopStoriesViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var mViewPager: ViewPager? = null
        var mIndicator: IndicatorView? = null

        init {
            mViewPager = itemView.findViewById(R.id.top_stories) as ViewPager
            mIndicator = itemView.findViewById(R.id.story_indicator) as IndicatorView
        }

        override fun bindView(position: Int) {
            super.bindView(position)
            val stories: ArrayList<View> = ArrayList()

            val listener:View.OnClickListener = View.OnClickListener {
                v ->  Toast.makeText(v.context,"click top stories${v.tag}",Toast.LENGTH_LONG).show()
                callback.toActivity(mTopStories[v.tag as Int].id.toString())
            }//未写
            for (i in 0 until  mTopStories.size) {
                val v: View = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.info_item_topstories, null)
                v.tag = i
                v.setOnClickListener(listener)
                val image: SimpleDraweeView = v.findViewById(R.id.image) as SimpleDraweeView
                image.setImageURI(mTopStories[i].image)
                val description: TextView = v.findViewById(R.id.description) as TextView
                description.text = mTopStories[i].title
                stories.add(v)
            }

            mViewPager!!.adapter = object : PagerAdapter() {
                override fun getCount(): Int {
                    return stories.size
                }

                override fun isViewFromObject(view: View, `object`: Any): Boolean {
                    return view == `object`
                }

                override fun instantiateItem(container: ViewGroup, position: Int): Any {
                    Log.d(LOG_TAG, "PagerAdapter instantiateItem position: $position")
                    container?.addView(stories[position])
                    return stories[position]
                }

                override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                    Log.d(LOG_TAG, "PagerAdapter destroyItem position: $position")
                    container?.removeView(stories[position])
                }
            }

            mViewPager!!.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    mIndicator?.moveIndicator(position,positionOffset,positionOffsetPixels)
                }
                override fun onPageSelected(position: Int) {
                   mIndicator?.setCurrentPosition(position)
                }
            })
        }
    }

    data class StoryWrapper(val type: Int, val imageUrl: String, val url: String, val title: String, val des: String)

    interface FragmentCallBack{
        fun toActivity(id: String)
    }
}


