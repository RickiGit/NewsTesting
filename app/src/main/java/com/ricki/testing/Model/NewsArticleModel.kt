package com.ricki.testing.Model

import java.io.Serializable

data class NewsArticleModel(
    var status : String,
    var totalResults : Int,
    var articles : ArrayList<NewsArticleItemModel>
) : Serializable

data class NewsArticleItemModel(
    var source : SourceArticleItemModel,
    var author : String,
    var title : String,
    var description : String,
    var url : String,
    var urlToImage : String,
    var publishedAt : String,
    var content : String
) : Serializable

data class SourceArticleItemModel(
    var id : String,
    var name : String
) : Serializable