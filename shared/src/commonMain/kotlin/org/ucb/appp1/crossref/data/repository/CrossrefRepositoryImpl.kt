package org.ucb.appp1.crossref.data.repository

import org.ucb.appp1.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.crossref.data.mapper.toDomain
import org.ucb.appp1.crossref.domain.model.WorkModel
import org.ucb.appp1.crossref.domain.repository.CrossrefRepository

class CrossrefRepositoryImpl(
    private val dataSource: CrossrefRemoteDataSource
) : CrossrefRepository {
    override suspend fun getWorks(query: String, rows: Int): Result<List<WorkModel>> {
        return runCatching {
            val response = dataSource.getWorks(query, rows)
            response.message?.items?.map { it.toDomain() } ?: emptyList()
        }
    }
}
