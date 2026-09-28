package org.ucb.appp1.crossref.presentation.viewmodel

import org.ucb.appp1.crossref.domain.model.WorkModel

data class CrossrefState(
    val query: String = "machine learning",
    val rows: Int = 3,
    val works: List<WorkModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
