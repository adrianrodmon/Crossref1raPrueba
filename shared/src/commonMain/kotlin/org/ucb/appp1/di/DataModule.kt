package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.crossref.data.repository.CrossrefRepositoryImpl
import org.ucb.appp1.crossref.data.service.CrossrefApiService
import org.ucb.appp1.crossref.domain.repository.CrossrefRepository
import org.ucb.appp1.userinformation.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.userinformation.data.repository.GithubRepositoryImpl
import org.ucb.appp1.userinformation.data.service.GitHubApiService
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

val dataModule = module {
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
    single<CrossrefRemoteDataSource> { CrossrefApiService() }
    single<CrossrefRepository> { CrossrefRepositoryImpl(get()) }
}
