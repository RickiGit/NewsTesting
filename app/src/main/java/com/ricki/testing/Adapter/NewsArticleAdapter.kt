package com.ricki.testing.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ricki.testing.Model.NewsArticleItemModel
import com.ricki.testing.Model.NewsSourcesItemModel
import com.ricki.testing.R
import com.ricki.testing.View.Fragments.CategoriesFragmentDirections
import com.ricki.testing.View.Fragments.NewsArticlesFragmentDirections
import com.ricki.testing.View.Fragments.NewsSourcesFragmentDirections
import com.ricki.testing.databinding.ItemLayoutArticlesBinding
import com.ricki.testing.databinding.ItemLayoutSourcesBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsArticleAdapter(var listOfNewsArticle: ArrayList<NewsArticleItemModel>) : RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>() {

    var dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    var parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    class NewsArticleViewHolder(val itemLayoutArticlesBinding: ItemLayoutArticlesBinding) : RecyclerView.ViewHolder(itemLayoutArticlesBinding.root)

    fun updateData(_listOfNewsArticle: ArrayList<NewsArticleItemModel>, isRemove : Boolean){
        if(isRemove){
            listOfNewsArticle.clear()
        }

        listOfNewsArticle.addAll(_listOfNewsArticle)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsArticleViewHolder (
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_layout_articles, parent, false)
    )

    override fun getItemCount(): Int {
        return listOfNewsArticle.size
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        var item = listOfNewsArticle[position]
        holder.itemLayoutArticlesBinding.textviewTitle.text = item.title
        holder.itemLayoutArticlesBinding.textviewPublished.text = dateFormat.format(parseFormat.parse(item.publishedAt))
        Glide
            .with(holder.itemView.context)
            .load(item.urlToImage)
            .centerCrop()
            .placeholder(R.drawable.custom_loading_spinner)
            .into(holder.itemLayoutArticlesBinding.imageviewArticle)

        holder.itemLayoutArticlesBinding.cardviewArticle.setOnClickListener {
            var action = NewsArticlesFragmentDirections.articlesToDetail(item)
            Navigation.findNavController(it).navigate(action)
        }
    }

}