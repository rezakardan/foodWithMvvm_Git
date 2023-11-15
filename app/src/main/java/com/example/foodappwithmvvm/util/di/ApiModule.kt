package com.example.foodappwithmvvm.util.di

import com.example.foodappwithmvvm.data.server.ApiService
import com.example.foodappwithmvvm.util.BASE_URL
import com.example.foodappwithmvvm.util.TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Provides
    @Singleton
    fun provideBaseUrl()= BASE_URL

    @Provides
    @Singleton
    fun provideTimeOut()=TIMEOUT

    @Provides
    @Singleton
    fun provideGson():Gson=GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideBodyInterCeptor()=HttpLoggingInterceptor().apply {

        level=HttpLoggingInterceptor.Level.BODY


    }

    @Provides
    @Singleton
    fun provideApiClient(time:Long,body:HttpLoggingInterceptor)=OkHttpClient.Builder()
        .addInterceptor(body)
        .readTimeout(time,TimeUnit.SECONDS)
        .connectTimeout(time,TimeUnit.SECONDS)
        .writeTimeout(time,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl:String,gson:Gson,client:OkHttpClient):ApiService=Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))

        .build()
        .create(ApiService::class.java)



}