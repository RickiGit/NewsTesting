package com.ricki.testing.Service

import com.ricki.testing.Constants.GlobalVariable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {

    var httpClientBuilder = OkHttpClient.Builder().addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder().header("Authorization", "Bearer " + GlobalVariable().API_KEY)
        val request = requestBuilder.build()
        it.proceed(request)
    }

    val httpClient = httpClientBuilder.build()

    // For Http Request
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GlobalVariable().BASE_URL)
        .client(httpClient)
        .build()

}