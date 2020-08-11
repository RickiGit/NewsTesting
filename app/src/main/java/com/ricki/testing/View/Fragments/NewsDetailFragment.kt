package com.ricki.testing.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.ricki.testing.R
import com.ricki.testing.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private lateinit var dataBinding : FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_detail, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Initiate Webview
        var webviewContent = dataBinding.webviewArticle

        // Set Custom Webview
        webviewContent.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            // Hide Loading After Finish Load Web
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dataBinding.progressbarDetail.visibility = View.GONE
            }
        }

        // Get Object From Page Article (Navigation)
        arguments?.let {
            var detailArticle = NewsDetailFragmentArgs.fromBundle(it).articleItem
            webviewContent.loadUrl(detailArticle.url)
        }
    }
}