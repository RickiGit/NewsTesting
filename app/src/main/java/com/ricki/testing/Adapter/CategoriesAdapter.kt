package com.ricki.testing.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ricki.testing.Model.CategoriesModel
import com.ricki.testing.R
import com.ricki.testing.View.Fragments.CategoriesFragmentDirections
import com.ricki.testing.databinding.ItemLayoutCategoriesBinding

class CategoriesAdapter(val listOfNewCategories: ArrayList<CategoriesModel>): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(
        val itemCategoriesBinding: ItemLayoutCategoriesBinding
    ) : RecyclerView.ViewHolder(itemCategoriesBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_layout_categories, parent, false)
    )

    override fun getItemCount(): Int {
        return listOfNewCategories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        var item = listOfNewCategories[position]
        holder.itemCategoriesBinding.textviewCategories.text = item.category
        holder.itemCategoriesBinding.cardviewCategories.setOnClickListener {
            var action = CategoriesFragmentDirections.categoryToNewsSources(item.category)
            Navigation.findNavController(it).navigate(action)
        }
    }
}