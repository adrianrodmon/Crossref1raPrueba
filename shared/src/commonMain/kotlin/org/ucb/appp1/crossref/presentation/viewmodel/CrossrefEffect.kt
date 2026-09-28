package org.ucb.appp1.crossref.presentation.viewmodel

sealed interface CrossrefEffect {
    data class ShowToast(val message: String) : CrossrefEffect
}
