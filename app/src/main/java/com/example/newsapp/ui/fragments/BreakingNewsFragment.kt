package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView(view)

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                Log.d("ARTICLE_WEB_SHOW", "NOT SHOWN ${it.source}")
                Log.d("ARTICLE_WEB_SHOW", "NOT SHOWN ${it}")
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleNewsFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar(view)
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar(view)
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar(view)
                }
            }

        })
    }

    private fun hideProgressBar(view: View) {
        val paginationProgressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(view: View) {
        val paginationProgressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(view: View) {
        newsAdapter = NewsAdapter()
        val rvBreakingNews = view.findViewById<RecyclerView>(R.id.rvBreakingNews)

        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}