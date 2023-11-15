package com.example.foodappwithmvvm.data.repository

import com.example.foodappwithmvvm.data.database.FoodDao
import javax.inject.Inject

class FavoriteRepository@Inject constructor(private val dao:FoodDao) {

    fun getAllFoods()=dao.getAllFoods()







}