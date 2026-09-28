package org.ucb.appp1.crossref.domain.repository

import org.ucb.appp1.crossref.domain.model.WorkModel

interface CrossrefRepository {
    suspend fun getWorks(query: String, rows: Int = 10): Result<List<WorkModel>>
}
