package com.ricki.testing.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ricki.testing.Model.NewsSourcesItemModel
import com.ricki.testing.R
import com.ricki.testing.View.Fragments.NewsSourcesFragmentDirections
import com.ricki.testing.databinding.ItemLayoutSourcesBinding

class NewsSourcesAdapter(var listOfNewsSources: ArrayList<NewsSourcesItemModel>): RecyclerView.Adapter<NewsSourcesAdapter.NewsSourcesViewHolder>() {

    class NewsSourcesViewHolder(val itemLayoutSourcesBinding: ItemLayoutSourcesBinding) : RecyclerView.ViewHolder(itemLayoutSourcesBinding.root)

    fun updateData(_listOfNewsSources: ArrayList<NewsSourcesItemModel>){
        listOfNewsSources.clear()
        listOfNewsSources.addAll(_listOfNewsSources)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsSourcesViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_layout_sources, parent, false)
    )

    override fun getItemCount(): Int {
        return listOfNewsSources.size
    }

    override fun onBindViewHolder(holder: NewsSourcesViewHolder, position: Int) {
        var item = listOfNewsSources[position]
        holder.itemLayoutSourcesBinding.textviewName.text = item.name
        holder.itemLayoutSourcesBinding.textviewDescription.text = item.description
        holder.itemLayoutSourcesBinding.textviewCategory.text = item.category

        holder.itemLayoutSourcesBinding.cardviewSources.setOnClickListener {
            var action = NewsSourcesFragmentDirections.sourcesToArticles(item.id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}