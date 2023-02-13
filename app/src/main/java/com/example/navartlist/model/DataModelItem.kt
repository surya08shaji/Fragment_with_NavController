package com.example.navartlist.model


data class DataModelItem(
    val id: Int,
    val audio: Any,
    val author: String,
    val category: List<Category>,
    val category_list: List<CategoryX>,
    val content: String,
    val date: String,
    val favorite: Boolean,
    val hide_title: Boolean,
    val image: String,
    val magazin: List<Magazin>,
    val share_text: String,
    val slug: Any,
    val title: String,
    val url: String
)