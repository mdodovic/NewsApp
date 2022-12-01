package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import com.example.newsapp.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}