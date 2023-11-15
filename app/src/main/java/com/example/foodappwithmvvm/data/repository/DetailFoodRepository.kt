package com.example.foodappwithmvvm.data.repository

import coil.size.Dimension
import com.example.foodappwithmvvm.data.database.FoodDao
import com.example.foodappwithmvvm.data.database.FoodEntity
import com.example.foodappwithmvvm.data.model.ResponseFoodsList
import com.example.foodappwithmvvm.data.server.ApiService
import com.example.foodappwithmvvm.util.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailFoodRepository@Inject constructor(private val api:ApiService,private val dao:FoodDao) {

    suspend fun detailFood(foodId:Int):Flow<MyResponse<ResponseFoodsList>>{

        return flow {

            emit(MyResponse.loading())

            when(api.detailFood(foodId).code()){

                in 200..202->{

                    emit(MyResponse.success(api.detailFood(foodId).body()))
                }



                in 400..499->{

                    emit(MyResponse.error(""))
                }




            }




        }.flowOn(Dispatchers.IO)
            .catch { emit(MyResponse.error(it.message.toString())) }




    }


suspend fun saveFood(food:FoodEntity)=dao.saveFood(food)


    suspend fun deleteFood(food: FoodEntity)=dao.deleteFood(food)


    fun getAllFood()=dao.getAllFoods()


    fun existFood(id:Int)=dao.existFood(id)




}