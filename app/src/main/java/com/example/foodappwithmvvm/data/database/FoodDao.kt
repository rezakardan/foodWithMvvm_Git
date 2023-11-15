package com.example.foodappwithmvvm.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodappwithmvvm.util.TABLE_FOOD
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun saveFood(food:FoodEntity)

   @Delete
    suspend fun deleteFood(food: FoodEntity)


    @Query("SELECT*FROM $TABLE_FOOD")
    fun getAllFoods():Flow<MutableList<FoodEntity>>


    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_FOOD WHERE id=:id)")
    fun existFood(id:Int):Flow<Boolean>






}