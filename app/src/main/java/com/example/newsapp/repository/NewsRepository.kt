package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(contryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(contryCode, pageNumber)
}