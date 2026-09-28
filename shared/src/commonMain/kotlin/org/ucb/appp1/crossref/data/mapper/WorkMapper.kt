package org.ucb.appp1.crossref.data.mapper

import org.ucb.appp1.crossref.data.dto.AuthorDto
import org.ucb.appp1.crossref.data.dto.PublishedDateDto
import org.ucb.appp1.crossref.data.dto.WorkDto
import org.ucb.appp1.crossref.domain.model.WorkModel

fun WorkDto.toDomain(): WorkModel {
    val titleText = title?.firstOrNull { it.isNotBlank() } ?: "Sin título"

    val authorsText = author?.mapNotNull { it.formatName() }
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString(", ")
        ?: "Autor(es) no especificados"

    val publishedText = formatPublishedDate(published, publishedPrint, publishedOnline, issued)

    val containerText = containerTitle?.firstOrNull { it.isNotBlank() } ?: "Sin revista/publicación"

    return WorkModel(
        title = titleText,
        authors = authorsText,
        publishedDate = publishedText,
        containerTitle = containerText,
        doi = doi ?: "N/A",
        type = type ?: "No especificado",
        url = url ?: ""
    )
}

private fun AuthorDto.formatName(): String? {
    val nameGiven = given?.trim()
    val nameFamily = family?.trim()
    val fullName = name?.trim()

    return when {
        !nameGiven.isNullOrEmpty() && !nameFamily.isNullOrEmpty() -> "$nameGiven $nameFamily"
        !nameFamily.isNullOrEmpty() -> nameFamily
        !nameGiven.isNullOrEmpty() -> nameGiven
        !fullName.isNullOrEmpty() -> fullName
        else -> null
    }
}

private fun formatPublishedDate(vararg candidates: PublishedDateDto?): String {
    for (candidate in candidates) {
        val dateList = candidate?.dateParts?.firstOrNull()
        if (!dateList.isNullOrEmpty()) {
            return dateList.joinToString("-") { it.toString().padStart(2, '0') }
        }
    }
    return "Fecha no disponible"
}
