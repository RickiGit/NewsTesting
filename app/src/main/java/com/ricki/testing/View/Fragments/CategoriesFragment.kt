package com.ricki.testing.View.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ricki.testing.Adapter.CategoriesAdapter
import com.ricki.testing.R
import com.ricki.testing.ViewModel.CategoriesViewModel
import com.ricki.testing.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    // Initialize Binding and ViewModel Categories
    private lateinit var viewModel : CategoriesViewModel
    private lateinit var dataBinding : FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        viewModel = activity?.run {ViewModelProviders.of(this).get(CategoriesViewModel::class.java)} ?: throw Exception("Failed Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)

        // Call Function for Create RecyclerView List
        setBindingCategories()

        return dataBinding.root
    }

    fun setBindingCategories(){
        // Set Categories to RecyclerView
        viewModel.listOfCategories.observe({ lifecycle }, {
            val categoryAdapter = CategoriesAdapter(it)
            val recyclerView = dataBinding.recyclerViewCategories

            // Apply RecyclerView
            recyclerView.apply {
                this.layoutManager = LinearLayoutManager(this.context)
                this.adapter = categoryAdapter
            }
        })
    }
}