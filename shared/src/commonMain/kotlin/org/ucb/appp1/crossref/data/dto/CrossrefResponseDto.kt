package org.ucb.appp1.crossref.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrossrefResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("message") val message: CrossrefMessageDto? = null
)

@Serializable
data class CrossrefMessageDto(
    @SerialName("total-results") val totalResults: Int? = null,
    @SerialName("items") val items: List<WorkDto>? = null
)

@Serializable
data class WorkDto(
    @SerialName("title") val title: List<String>? = null,
    @SerialName("author") val author: List<AuthorDto>? = null,
    @SerialName("published") val published: PublishedDateDto? = null,
    @SerialName("published-print") val publishedPrint: PublishedDateDto? = null,
    @SerialName("published-online") val publishedOnline: PublishedDateDto? = null,
    @SerialName("issued") val issued: PublishedDateDto? = null,
    @SerialName("container-title") val containerTitle: List<String>? = null,
    @SerialName("DOI") val doi: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("URL") val url: String? = null
)

@Serializable
data class AuthorDto(
    @SerialName("given") val given: String? = null,
    @SerialName("family") val family: String? = null,
    @SerialName("name") val name: String? = null
)

@Serializable
data class PublishedDateDto(
    @SerialName("date-parts") val dateParts: List<List<Int>>? = null
)
