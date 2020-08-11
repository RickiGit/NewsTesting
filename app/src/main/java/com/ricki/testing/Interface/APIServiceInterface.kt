package com.ricki.testing.Interface

import com.ricki.testing.Model.NewsArticleModel
import com.ricki.testing.Model.NewsSourcesModel
import com.ricki.testing.Service.APIService
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServiceInterface {

    @GET("sources")
    suspend fun getNewsSources(@Query("category") category:String, @Query("language") language:String, @Query("country") country:String): NewsSourcesModel

    @GET("everything")
    suspend fun getArticle(@Query("sources") sources:String, @Query("q") search:String, @Query("pageSize") pageSize:Int, @Query("page") page : Int): NewsArticleModel

    object APIAllServices {
        val retrofitService = APIService().retrofit.create(APIServiceInterface::class.java)
    }
}