package org.ucb.appp1.crossref.presentation.viewmodel

sealed interface CrossrefEvent {
    data class OnQueryChange(val newQuery: String) : CrossrefEvent
    data object OnSearch : CrossrefEvent
}
