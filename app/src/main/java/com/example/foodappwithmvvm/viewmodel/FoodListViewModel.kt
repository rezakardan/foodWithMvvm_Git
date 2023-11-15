package com.example.foodappwithmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappwithmvvm.data.model.ResponseCategoriesList
import com.example.foodappwithmvvm.data.model.ResponseFoodsList
import com.example.foodappwithmvvm.data.repository.FoodListRepository
import com.example.foodappwithmvvm.util.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(private val repository: FoodListRepository) :
    ViewModel() {

init {
    randomFood()
    categoryList()
}

    val random=MutableLiveData<List<ResponseFoodsList.Meal>>()

fun randomFood()=viewModelScope.launch {

   repository.randomFood().collect{

       random.postValue(it.body()?.meals!!)



   }


}

    val category=MutableLiveData<MyResponse<ResponseCategoriesList>>()
    fun categoryList()=viewModelScope.launch(Dispatchers.IO) {

        repository.categoryFood().collect{


            category.postValue(it)



        }




    }


    val allFoods=MutableLiveData<MyResponse<ResponseFoodsList>>()
    fun allFoodList(letter:String)=viewModelScope.launch(Dispatchers.IO) {

        repository.allFoodList(letter).collect{

            allFoods.postValue(it)



        }





    }



    fun searchFood(letter: String)=viewModelScope.launch(Dispatchers.IO) {

      repository.searchFood(letter).collect{


          allFoods.postValue(it)



      }





    }

    val filterSpinner=MutableLiveData<List<Char>>()
    fun filterSpinner()=viewModelScope.launch {

        val spinnerList= arrayListOf('A'..'Z').flatten()


        filterSpinner.postValue(spinnerList)



    }




    fun filterByCategory(categoryName:String)=viewModelScope.launch {

        repository.filterByCategory(categoryName).collect{

            allFoods.postValue(it)



        }





    }


}




