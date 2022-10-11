package com.optimum.optimumreport.retrofit

import com.optimum.optimumreport.utility.URLConstant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()


    val instance: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().excludeFieldsWithModifiers(
                        Modifier.TRANSIENT
                    )
                        .disableHtmlEscaping().create()
                )
            )
            .client(mOkHttpClient)
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}