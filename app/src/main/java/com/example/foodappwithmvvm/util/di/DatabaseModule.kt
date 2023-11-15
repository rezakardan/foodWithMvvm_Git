package com.example.foodappwithmvvm.util.di

import android.content.Context
import androidx.room.Room
import com.example.foodappwithmvvm.data.database.FoodDatabase
import com.example.foodappwithmvvm.data.database.FoodEntity
import com.example.foodappwithmvvm.util.FOOD_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideEntity()=FoodEntity()


    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context)=Room.databaseBuilder(context,FoodDatabase::class.java,
        FOOD_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
            fun provideDatabase(db:FoodDatabase)=db.foodDao()

}