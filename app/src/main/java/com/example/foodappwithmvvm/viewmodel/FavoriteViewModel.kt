package com.example.foodappwithmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappwithmvvm.data.database.FoodEntity
import com.example.foodappwithmvvm.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel@Inject constructor(private val repository: FavoriteRepository):ViewModel() {


    val getAllData=MutableLiveData<MutableList<FoodEntity>>()

    val emptyList=MutableLiveData<Boolean>()

    fun getAllFoods()=viewModelScope.launch {

        repository.getAllFoods().collect{

            if (it.isNotEmpty()) {

                getAllData.postValue(it)
emptyList.postValue(false)
            }else{


                emptyList.postValue(true)
            }

        }




    }




}