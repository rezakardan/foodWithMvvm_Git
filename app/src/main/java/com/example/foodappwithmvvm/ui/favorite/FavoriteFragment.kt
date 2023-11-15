package com.example.foodappwithmvvm.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodappwithmvvm.R
import com.example.foodappwithmvvm.databinding.FragmentFavoriteBinding
import com.example.foodappwithmvvm.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {


lateinit var binding: FragmentFavoriteBinding


private val viewModel:FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter:FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoriteBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    viewModel.getAllFoods()

        viewModel.getAllData.observe(viewLifecycleOwner){



            binding.favoriteList.visibility=View.VISIBLE

            binding.emptyLay.visibility=View.GONE


            adapter.setData(it)

            binding.favoriteList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            binding.favoriteList.adapter=adapter





        }


        viewModel.emptyList.observe(viewLifecycleOwner){

            if (it){

                binding.favoriteList.visibility=View.GONE

                binding.emptyLay.visibility=View.VISIBLE

                binding.statusLay.disImg.setImageResource(R.drawable.box)

                binding.statusLay.disTxt.text=getString(R.string.emptyList)





            }else{

                binding.favoriteList.visibility=View.VISIBLE

                binding.emptyLay.visibility=View.GONE


            }



        }


        adapter.setOnItemClickListener {

            val direction=FavoriteFragmentDirections.actionToDetailFragment(it.id)

            findNavController().navigate(direction)


        }


    }


}