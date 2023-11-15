package com.example.foodappwithmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodappwithmvvm.R
import com.example.foodappwithmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding


private lateinit var navHost:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment


        binding.bottomNav.setupWithNavController(navHost.navController)



        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
           if (destination.id==R.id.detailFragment){

               binding.bottomNav.visibility=View.GONE



           }else{

               binding.bottomNav.visibility=View.VISIBLE


           }
        }


    }


    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp()|| super.onNavigateUp()
    }
}