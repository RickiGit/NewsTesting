package com.ricki.testing.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ricki.testing.Adapter.NewsSourcesAdapter
import com.ricki.testing.R
import com.ricki.testing.ViewModel.NewsSourcesViewModel
import com.ricki.testing.databinding.FragmentNewsSourcesBinding

class NewsSourcesFragment : Fragment() {

    // Initialize Binding and ViewModel News Sources
    private lateinit var viewModel : NewsSourcesViewModel
    private lateinit var dataBinding : FragmentNewsSourcesBinding
    var newsSourcesAdapter = NewsSourcesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initiate ViewModel
        viewModel = ViewModelProviders.of(this).get(NewsSourcesViewModel::class.java)
        viewModel = activity?.run { ViewModelProviders.of(this).get(NewsSourcesViewModel::class.java)} ?: throw Exception("Failed Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_sources, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate RecyclerView
        dataBinding.recyclerViewNewsSources.apply {
            this.layoutManager = LinearLayoutManager(dataBinding.root.context)
            this.adapter = newsSourcesAdapter
        }

        // Fetch All News Sources
        arguments?.let {
            var categoryItem = NewsSourcesFragmentArgs.fromBundle(it).categoryItem
            viewModel.fetchNewsSources(categoryItem, "", "")
        }

        // Call Method Binding Data
        getAllNewsSources()
        setLoadingRequest()
    }

    fun getAllNewsSources(){
        // Set News Sources to RecyclerView
        viewModel.listOfNewsSource.observe({ lifecycle }, {
            newsSourcesAdapter.updateData(it)
        })
    }

    fun setLoadingRequest(){
        // Create Loading When Request Async
        viewModel.isLoading.observe({lifecycle},{
            val progressBar = dataBinding.progressbarNewsSources
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}