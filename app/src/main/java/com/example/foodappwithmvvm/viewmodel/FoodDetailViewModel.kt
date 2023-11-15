package com.example.foodappwithmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappwithmvvm.data.database.FoodEntity
import com.example.foodappwithmvvm.data.model.ResponseFoodsList
import com.example.foodappwithmvvm.data.repository.DetailFoodRepository
import com.example.foodappwithmvvm.util.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodDetailViewModel @Inject constructor(private val repository: DetailFoodRepository) :
    ViewModel() {


    val foodDetails = MutableLiveData<MyResponse<ResponseFoodsList>>()
    fun detailFood(foodId: Int) = viewModelScope.launch(Dispatchers.IO) {


        repository.detailFood(foodId).collect {


            foodDetails.postValue(it)


        }


    }


   fun saveFood(food:FoodEntity)=viewModelScope.launch {

       repository.saveFood(food)



   }



    fun deleteFood(food: FoodEntity)=viewModelScope.launch {


        repository.deleteFood(food)


    }


    val existsOrNo=MutableLiveData<Boolean>()

fun existFood(id:Int)=viewModelScope.launch {

    repository.existFood(id).collect{

       existsOrNo.postValue(it)




    }




}
}