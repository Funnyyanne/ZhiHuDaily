package com.anne.zhihudaily.model

data class LatestInfoJson(
    /***
     * {"date": "20210715",
     * "stories": [{"image_hue": "0x2c1117","title": "瞎扯 · 如何正确地吐槽","url": "https://daily.zhihu.com/story/9738098","hint": "VOL.2701","ga_prefix": "071506","images": ["https://pic1.zhimg.com/v2-2b2ec3f12c41bb45173c07c29c0aaf51.jpg?source=8673f162"],"type": 0,"id": 9738098}],
     * "top_stories": [{"image_hue": "0x9e6848","hint": "作者 / ag獭","url": "https://daily.zhihu.com/story/9738008","image": "https://pic2.zhimg.com/v2-7f806d20519da92ca62301ef913e34f7.jpg?source=8673f162","title": "世界各地都有哪些菜系？如何形成的？","ga_prefix": "071307","type": 0,"id": 9738008},{"image_hue": "0x4d3a36","hint": "作者 / 吴怼怼","url": "https://daily.zhihu.com/story/9737880","image": "https://pic2.zhimg.com/v2-8e7c232606491632b50d6b04f27b147d.jpg?source=8673f162","title": "翻拍的电影电视剧为什么总是不尽如人意？","ga_prefix": "070907","type": 0,"id": 9737880}]}
     */
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
)

/**
 *   "image_hue": "0x727da3",
    "title": "911 事件的原貌是怎样的？（深度超长文）",
    "url": "https://daily.zhihu.com/story/9738091",
    "hint": "智先生 · 37 分钟阅读",
    "ga_prefix": "071507",
    "images": [
    "https://pic1.zhimg.com/v2-8545b6541d82073d7d2dcd98bd547724.jpg?source=8673f162"
    ],
    "type": 0,
    "id": 9738091
 */
data class Story(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)

/**
 *
 *  "image_hue": "0x9e6848",
    "hint": "作者 / ag獭",
    "url": "https://daily.zhihu.com/story/9738008",
    "image": "https://pic2.zhimg.com/v2-7f806d20519da92ca62301ef913e34f7.jpg?source=8673f162",
    "title": "世界各地都有哪些菜系？如何形成的？",
    "ga_prefix": "071307",
    "type": 0,
    "id": 9738008
 */
data class TopStory(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image: String,
    val image_hue: String,
    val title: String,
    val type: Int,
    val url: String
)