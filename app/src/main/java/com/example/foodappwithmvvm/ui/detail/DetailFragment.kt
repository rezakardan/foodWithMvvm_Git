package com.example.foodappwithmvvm.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.foodappwithmvvm.R
import com.example.foodappwithmvvm.data.database.FoodDao
import com.example.foodappwithmvvm.data.database.FoodEntity
import com.example.foodappwithmvvm.databinding.FragmentDetailBinding
import com.example.foodappwithmvvm.util.CheckConnection
import com.example.foodappwithmvvm.util.MyResponse
import com.example.foodappwithmvvm.util.YOTUBE_VIDEOID
import com.example.foodappwithmvvm.viewmodel.FoodDetailViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var connection: CheckConnection

    val args: DetailFragmentArgs by navArgs()

    var foodId = -1


    val viewModel: FoodDetailViewModel by viewModels()

    @Inject
    lateinit var entity: FoodEntity

    private var isFavorite=false

    enum class PageState { NETWORK, NONE }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        foodId = args.foodId


        binding.detailBack.setOnClickListener { findNavController().navigateUp() }


        viewModel.detailFood(foodId)

        viewModel.foodDetails.observe(viewLifecycleOwner) {

            when (it.status) {
                MyResponse.Status.LOADING -> {
                    binding.detailLoading.visibility = View.VISIBLE
                    binding.detailContentLay.visibility = View.GONE
                }
                MyResponse.Status.SUCCESS -> {
                    binding.detailLoading.visibility = View.GONE
                    binding.detailContentLay.visibility = View.VISIBLE
                    //Set data
                    it.data?.meals?.get(0)?.let { itMeal ->


                        entity.id = itMeal.idMeal!!.toInt()
                        entity.title = itMeal.strMeal.toString()

                        entity.img = itMeal.strMealThumb.toString()

                        //Set data
                        binding.foodCoverImg.load(itMeal.strMealThumb) {
                            crossfade(true)
                            crossfade(500)
                        }
                        binding.foodCategoryTxt.text = itMeal.strCategory
                        binding.foodAreaTxt.text = itMeal.strArea
                        binding.foodTitleTxt.text = itMeal.strMeal
                        binding.foodDescTxt.text = itMeal.strInstructions
                        //Play
                        if (itMeal.strYoutube != null) {
                            binding.foodPlayImg.visibility = View.VISIBLE
                            binding.foodPlayImg.setOnClickListener {

                                val intent=Intent(Intent.ACTION_VIEW,Uri.parse(itMeal.strYoutube))
                                startActivity(intent)
                                }
                            }
                         else {
                            binding.foodPlayImg.visibility = View.GONE
                        }
                        //Source
                        if (itMeal.strSource != null) {
                            binding.foodSourceImg.visibility = View.VISIBLE
                            binding.foodSourceImg.setOnClickListener {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(itMeal.strSource)
                                    )
                                )
                            }
                        } else {
                            binding.foodSourceImg.visibility = View.GONE
                        }
                    }
                    //Json Array
                    val jsonData = JSONObject(Gson().toJson(it.data))
                    val meals = jsonData.getJSONArray("meals")
                    val meal = meals.getJSONObject(0)
                    //Ingredient
                    for (i in 1..15) {
                        val ingredient = meal.getString("strIngredient$i")
                        if (ingredient.isNullOrEmpty().not()) {
                            binding.ingredientsTxt.append("$ingredient\n")
                        }
                    }
                    //Measure
                    for (i in 1..15) {
                        val measure = meal.getString("strMeasure$i")
                        if (measure.isNullOrEmpty().not()) {
                            binding.measureTxt.append("$measure\n")
                        }
                    }
                }


                MyResponse.Status.ERROR -> {

                    binding.detailLoading.visibility = View.GONE

                    binding.homeDisLay.visibility = View.VISIBLE

                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()


                }


            }


        }



viewModel.existFood(foodId)

        viewModel.existsOrNo.observe(viewLifecycleOwner){

            isFavorite=it
            if (it){

                binding.detailFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.tartOrange))




            }else{

                binding.detailFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
            }




        }



        binding.detailFav.setOnClickListener {

            if (isFavorite){

                viewModel.deleteFood(entity)



            }else{

                viewModel.saveFood(entity)
            }



        }









        connection.observe(viewLifecycleOwner) {


            if (it) {

                checkConnection(false, PageState.NONE)


            } else {
                checkConnection(true, PageState.NETWORK)


            }


        }


    }


    private fun checkConnection(showErrorOrNo: Boolean, state: PageState) {

        if (showErrorOrNo) {

            binding.detailContentLay.visibility = View.GONE

            binding.homeDisLay.visibility = View.VISIBLE




            when (state) {

                PageState.NETWORK -> {

                    binding.statusLay.disTxt.text = getString(R.string.checkInternet)

                    binding.statusLay.disImg.setImageResource(R.drawable.disconnect)


                }

                else -> {}


            }


        } else {


            binding.detailContentLay.visibility = View.VISIBLE

            binding.homeDisLay.visibility = View.GONE


        }


    }


}