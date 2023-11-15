package com.example.foodappwithmvvm.data.server

import com.example.foodappwithmvvm.data.model.ResponseCategoriesList
import com.example.foodappwithmvvm.data.model.ResponseFoodsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


@GET("random.php")
suspend fun randomFoodList():Response<ResponseFoodsList>

@GET("categories.php")
suspend fun categoryFood():Response<ResponseCategoriesList>

@GET("search.php")
suspend fun allFoodsList(@Query("f")letter:String):Response<ResponseFoodsList>

    @GET("search.php")
    suspend fun searchFood(@Query("s") letter: String): Response<ResponseFoodsList>


@GET("filter.php")
suspend fun filterFoodByCategory(@Query("c")categoryName:String):Response<ResponseFoodsList>

@GET("lookup.php")
suspend fun detailFood(@Query("i")id:Int):Response<ResponseFoodsList>

}