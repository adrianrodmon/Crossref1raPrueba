package org.ucb.appp1.crossref.domain.model

data class WorkModel(
    val title: String,
    val authors: String,
    val publishedDate: String,
    val containerTitle: String,
    val doi: String,
    val type: String,
    val url: String
)
