package com.example.foodappwithmvvm.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodappwithmvvm.R
import com.example.foodappwithmvvm.data.model.ResponseCategoriesList
import com.example.foodappwithmvvm.databinding.ItemCategoryRecyclerBinding
import javax.inject.Inject

class FoodListCategoryAdapter@Inject constructor():RecyclerView.Adapter<FoodListCategoryAdapter.FoodCategoryViewHolder>() {

    lateinit var binding: ItemCategoryRecyclerBinding
    var categoryList= emptyList<ResponseCategoriesList.Category>()


    private var itemSelected=-1
    inner class FoodCategoryViewHolder(item:View):RecyclerView.ViewHolder(item){


        fun onBind(oneItem:ResponseCategoriesList.Category){

            binding.imgCategory.load(oneItem.strCategoryThumb){
                crossfade(true)
                crossfade(500)
            }
            binding.categorytext.text=oneItem.strCategory


            binding.root.setOnClickListener {

                itemSelected=adapterPosition

                notifyDataSetChanged()


           onItemClicked?.let {

               it(oneItem)
           }


            }


            if (itemSelected==adapterPosition){

                binding.root.setBackgroundResource(R.drawable.bg_rounded_selcted)


            }else{

                binding.root.setBackgroundResource(R.drawable.bg_rounded_white)
            }


        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategoryViewHolder {
binding=ItemCategoryRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return FoodCategoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FoodCategoryViewHolder, position: Int) {
holder.onBind(categoryList[position])
    holder.setIsRecyclable(false)
    }

    override fun getItemCount()=categoryList.size




    private var onItemClicked:((ResponseCategoriesList.Category)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseCategoriesList.Category)->Unit){

        onItemClicked=listener



    }





    fun setData(data:List<ResponseCategoriesList.Category>){


        val categoryDiff= CategoryFoodDiffUtils(categoryList,data)

        val diff=DiffUtil.calculateDiff(categoryDiff)

        categoryList=data

diff.dispatchUpdatesTo(this)


    }


    class CategoryFoodDiffUtils(val oldItem:List<ResponseCategoriesList.Category>,val newItem:List<ResponseCategoriesList.Category>):DiffUtil.Callback(){
        override fun getOldListSize(): Int {

            return oldItem.size
        }

        override fun getNewListSize(): Int {
return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
return oldItem[oldItemPosition]===newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]
        }


    }


}