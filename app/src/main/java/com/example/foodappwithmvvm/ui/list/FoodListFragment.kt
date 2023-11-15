package com.example.foodappwithmvvm.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.foodappwithmvvm.R
import com.example.foodappwithmvvm.databinding.FragmentDetailBinding
import com.example.foodappwithmvvm.databinding.FragmentFoodListBinding
import com.example.foodappwithmvvm.ui.list.adapter.FoodListAdapter
import com.example.foodappwithmvvm.ui.list.adapter.FoodListCategoryAdapter
import com.example.foodappwithmvvm.util.CheckConnection
import com.example.foodappwithmvvm.util.MyResponse
import com.example.foodappwithmvvm.viewmodel.FoodListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FoodListFragment : Fragment() {

    lateinit var binding: FragmentFoodListBinding
    private val viewModel: FoodListViewModel by viewModels()

    @Inject
    lateinit var categoryFoodAdapter: FoodListCategoryAdapter


    @Inject
    lateinit var foodListAdapter: FoodListAdapter


    @Inject
    lateinit var connection:CheckConnection


    enum class PageState{NETWORK,EMPTY,NONE}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.randomFood()
        viewModel.random.observe(viewLifecycleOwner) {

            binding.imgHeader.load(it.get(0).strMealThumb) {
                crossfade(true)
                crossfade(500)
            }


        }





        viewModel.categoryList()
        viewModel.category.observe(viewLifecycleOwner) {


            when (it.status) {

                MyResponse.Status.LOADING -> {

                    binding.homeCategoryLoading.visibility = View.VISIBLE

                    binding.categoryList.visibility = View.GONE


                }


                MyResponse.Status.SUCCESS -> {


                    binding.homeCategoryLoading.visibility = View.GONE

                    binding.categoryList.visibility = View.VISIBLE

                    categoryFoodAdapter.setData(it.data!!.categories)

                    binding.categoryList.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                    binding.categoryList.adapter = categoryFoodAdapter


                }

                MyResponse.Status.ERROR -> {

                    binding.categoryList.visibility = View.VISIBLE

                    binding.homeCategoryLoading.visibility = View.GONE

                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()


                }


            }


        }




        viewModel.allFoodList("A")

        viewModel.allFoods.observe(viewLifecycleOwner) {

            when (it.status) {

                MyResponse.Status.LOADING -> {

                    binding.foodsList.visibility = View.GONE

                    binding.homeFoodsLoading.visibility = View.VISIBLE


                }

                MyResponse.Status.SUCCESS -> {

                    binding.foodsList.visibility = View.VISIBLE

                    binding.homeFoodsLoading.visibility = View.GONE


                    if (it.data?.meals!=null){

                        if (it.data.meals.isNotEmpty()){


                            checkConnectionOrEmpty(false,PageState.NONE)
                            foodListAdapter.setData(it.data.meals)

                            binding.foodsList.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            binding.foodsList.adapter = foodListAdapter

                        }

                    }else{

                        checkConnectionOrEmpty(true,PageState.EMPTY)

                    }






                }


                MyResponse.Status.ERROR -> {

                    binding.foodsList.visibility = View.VISIBLE

                    binding.homeFoodsLoading.visibility = View.GONE

                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()


                }


            }


        }



        binding.edtSearch.addTextChangedListener {

            val search = it.toString()

            if (search.length > 2) {

                viewModel.searchFood(search)


            }


        }


        viewModel.filterSpinner()
        viewModel.filterSpinner.observe(viewLifecycleOwner) {

            spinnerCategory(it)
        }



        categoryFoodAdapter.setOnItemClickListener {


            viewModel.filterByCategory(it.strCategory.toString())



        }





        foodListAdapter.setOnItemClick {

            val direction=FoodListFragmentDirections.actionToDetailFragment(it.idMeal!!.toInt())

findNavController().navigate(direction)

        }






        connection.observe(viewLifecycleOwner){

            if (it){
                checkConnectionOrEmpty(false,PageState.NONE)



            }else{

                checkConnectionOrEmpty(true,PageState.NETWORK)

            }



        }






    }


    private fun spinnerCategory(data: List<Char>) {

        val spinnerList = data

        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, spinnerList)


        adapter.setDropDownViewResource(R.layout.item_spinnerlist)

        binding.filterSpinner.adapter = adapter

        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                viewModel.allFoodList(spinnerList[p2].toString())


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


    }




    private fun checkConnectionOrEmpty(showErrorOrNo:Boolean,state:PageState){

        if (showErrorOrNo){

            binding.homeContent.visibility=View.GONE

            binding.homeDisLay.visibility=View.VISIBLE


            when(state){

                PageState.NETWORK->{

                    binding.statusLay2.disImg.setImageResource(R.drawable.disconnect)

                    binding.statusLay2.disTxt.text=getString(R.string.checkInternet)



                }

                PageState.EMPTY->{
                    binding.statusLay2.disImg.setImageResource(R.drawable.box)

                    binding.statusLay2.disTxt.text=getString(R.string.emptyList)
                }

                else->{

                }




            }






        }else{

            binding.homeContent.visibility=View.VISIBLE

            binding.homeDisLay.visibility=View.GONE

        }









    }




}




