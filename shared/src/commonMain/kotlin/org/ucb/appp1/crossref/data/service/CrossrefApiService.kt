package org.ucb.appp1.crossref.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ucb.appp1.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.crossref.data.dto.CrossrefResponseDto

class CrossrefApiService : CrossrefRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getWorks(query: String, rows: Int): CrossrefResponseDto {
        return client.get("https://api.crossref.org/works") {
            parameter("query", query)
            parameter("rows", rows)
        }.body<CrossrefResponseDto>()
    }
}
