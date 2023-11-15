package com.example.foodappwithmvvm.data.repository

import com.example.foodappwithmvvm.data.model.ResponseCategoriesList
import com.example.foodappwithmvvm.data.model.ResponseFoodsList
import com.example.foodappwithmvvm.data.server.ApiService
import com.example.foodappwithmvvm.util.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class FoodListRepository @Inject constructor(private val api: ApiService) {

    suspend fun randomFood(): Flow<Response<ResponseFoodsList>> {

        return flow {
            emit(api.randomFoodList())

        }


    }


    suspend  fun categoryFood(): Flow<MyResponse<ResponseCategoriesList>> {

        return flow {


            emit(MyResponse.loading())


            when (api.categoryFood().code()) {


                in 200..202 -> {

                    emit(MyResponse.success(api.categoryFood().body()))


                }

                in 400..499 -> {


                    emit(MyResponse.error(""))
                }

                in 500..599 -> {

                    emit(MyResponse.error(""))
                }


            }


        }.flowOn(Dispatchers.IO)
            .catch { emit(MyResponse.error(it.message.toString())) }


    }


    suspend  fun allFoodList(letter: String): Flow<MyResponse<ResponseFoodsList>> {


        return flow {
            emit(MyResponse.loading())
            when (api.allFoodsList(letter).code()) {

                in 200..202 -> {

                    emit(MyResponse.success(api.allFoodsList(letter).body()))


                }

                in 400..499 -> {

                    emit(MyResponse.error(""))
                }


            }


        }.flowOn(Dispatchers.IO)
            .catch { emit(MyResponse.error(it.message.toString())) }
    }


    suspend  fun searchFood(letter: String): Flow<MyResponse<ResponseFoodsList>> {


        return flow {


            emit(MyResponse.loading())
            when (api.searchFood(letter).code()) {

                in 200..202 -> {
                    emit(MyResponse.success(api.searchFood(letter).body()))
                }

                in 400..499 -> {
                    emit(MyResponse.error(""))
                }


            }


        }.flowOn(Dispatchers.IO)
            .catch { emit(MyResponse.error(it.message.toString())) }
    }


    suspend fun filterByCategory(categoryName: String): Flow<MyResponse<ResponseFoodsList>> {

        return flow {
            emit(MyResponse.loading())
            when (api.filterFoodByCategory(categoryName).code()) {

                in 200..202 -> {

                    emit(MyResponse.success(api.filterFoodByCategory(categoryName).body()))
                }


                in 400..499 -> {
                    emit(MyResponse.error(""))
                }


            }


        }.flowOn(Dispatchers.IO)
            .catch { emit(MyResponse.error(it.message.toString())) }


    }

}