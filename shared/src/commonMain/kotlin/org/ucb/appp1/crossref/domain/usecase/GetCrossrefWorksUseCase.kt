package org.ucb.appp1.crossref.domain.usecase

import org.ucb.appp1.crossref.domain.model.WorkModel
import org.ucb.appp1.crossref.domain.repository.CrossrefRepository

class GetCrossrefWorksUseCase(
    private val repository: CrossrefRepository
) {
    suspend operator fun invoke(query: String, rows: Int = 10): Result<List<WorkModel>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.getWorks(query, rows)
    }
}
