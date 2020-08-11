package com.ricki.testing.Model

import java.io.Serializable

data class NewsSourcesModel(
    val status: String,
    val sources: ArrayList<NewsSourcesItemModel>
) : Serializable

data class NewsSourcesItemModel (
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
) : Serializable