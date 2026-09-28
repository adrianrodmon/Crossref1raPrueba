package org.ucb.appp1.crossref.data.datasource

import org.ucb.appp1.crossref.data.dto.CrossrefResponseDto

interface CrossrefRemoteDataSource {
    suspend fun getWorks(query: String, rows: Int = 10): CrossrefResponseDto
}
