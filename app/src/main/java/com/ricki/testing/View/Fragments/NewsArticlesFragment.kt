package com.ricki.testing.View.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricki.testing.Adapter.NewsArticleAdapter
import com.ricki.testing.R
import com.ricki.testing.ViewModel.NewsArticleViewModel
import com.ricki.testing.databinding.FragmentNewsArticlesBinding
import kotlinx.android.synthetic.main.fragment_news_articles.*

class NewsArticlesFragment : Fragment() {

    // Initialize Binding and ViewModel News Sources
    private lateinit var viewModel : NewsArticleViewModel
    private lateinit var dataBinding : FragmentNewsArticlesBinding
    var newsArticleAdapter = NewsArticleAdapter(arrayListOf())
    private lateinit var linearLayoutManager: LinearLayoutManager

    // Variable for Infinite Scroll
    val pageSize = 15
    var page = 1
    var idSource = ""

    // Variable for Checking Status Scroll
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    var firstVisibleItem = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    var search = ""
    var isScroll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initiate View Model
        viewModel = ViewModelProviders.of(this).get(NewsArticleViewModel::class.java)
        viewModel = activity?.run { ViewModelProviders.of(this).get(NewsArticleViewModel::class.java)} ?: throw Exception("Failed Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_articles, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate RecyclerView
        linearLayoutManager = LinearLayoutManager(dataBinding.root.context)
        dataBinding.recyclerViewNewsArticles.layoutManager = linearLayoutManager
        dataBinding.recyclerViewNewsArticles.adapter = newsArticleAdapter

        // Fetch All News Sources
        arguments?.let {
            idSource = NewsArticlesFragmentArgs.fromBundle(it).idsource
            viewModel.fetchNewsArticle(idSource, search, pageSize, page)
        }

        getAllNewsArticle(false)
        setLoadingRequest()
        searchArticle()
        infiniteScroll()
        refreshPage()
        setStatusData()
    }

    fun searchArticle(){
        // Search Article
        imageview_search.setOnClickListener(View.OnClickListener {
            search = edittext_search.text.toString()
            page = 1
            viewModel.fetchNewsArticle(idSource, search, pageSize, page)
            getAllNewsArticle(true)
        })
    }

    fun getAllNewsArticle(isRemove : Boolean){
        // Set Article to RecyclerView
        viewModel.listOfNewsArticle.observe({ lifecycle }, {
            // Check Total Result
            if(it.size >= pageSize){
                // Ready for Scroll if Data more than 15
                isScroll = true
            }

            newsArticleAdapter.updateData(it, isRemove)
        })
    }

    fun setLoadingRequest(){
        // Create Loading When Request Article
        viewModel.isLoading.observe({lifecycle},{
            val progressBar = dataBinding.progressbarNewsArticles
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    fun setStatusData(){
        // Check Status Result
        viewModel.status.observe({lifecycle},{
            val textViewStatus = dataBinding.textviewStatus
            val recyclerView = dataBinding.recyclerViewNewsArticles
            textViewStatus.visibility = if (it.equals("failed")) View.VISIBLE else View.GONE
            recyclerView.visibility = if (it.equals("failed")) View.GONE else View.VISIBLE

        })
    }

    fun refreshPage(){
        // Refresh Page
        dataBinding.swiperefreshNewsArticle.setOnRefreshListener {
            dataBinding.swiperefreshNewsArticle.isRefreshing = false
            page = 1
            search = ""
            edittext_search.setText("")
            viewModel.fetchNewsArticle(idSource, search, pageSize, page)
            getAllNewsArticle(true)
        }
    }

    fun infiniteScroll(){
        // Function for Infinite Scroll
        dataBinding.recyclerViewNewsArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = dataBinding.recyclerViewNewsArticles.childCount
                totalItemCount = linearLayoutManager.itemCount
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }

                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold && isScroll) {
                    viewModel.fetchNewsArticle(idSource, search, pageSize, page)
                    loading = true
                    page += 1
                }
            }
        })
    }
}