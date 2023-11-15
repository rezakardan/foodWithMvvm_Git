package com.example.foodappwithmvvm.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodappwithmvvm.util.TABLE_FOOD

@Entity(tableName = TABLE_FOOD)
data class FoodEntity (

    @PrimaryKey()
    var id:Int=0,

    var title:String="",

    var img:String=""



        )