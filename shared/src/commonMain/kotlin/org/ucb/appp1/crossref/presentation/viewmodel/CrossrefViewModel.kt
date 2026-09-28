package org.ucb.appp1.crossref.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.crossref.domain.usecase.GetCrossrefWorksUseCase

class CrossrefViewModel(
    private val getCrossrefWorksUseCase: GetCrossrefWorksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CrossrefState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CrossrefEffect>()
    val effect = _effect.asSharedFlow()

    init {
        searchWorks()
    }

    fun emitEvent(event: CrossrefEvent) {
        when (event) {
            is CrossrefEvent.OnQueryChange -> {
                _state.update { it.copy(query = event.newQuery) }
            }
            CrossrefEvent.OnSearch -> {
                searchWorks()
            }
        }
    }

    private fun searchWorks() {
        val currentQuery = _state.value.query
        if (currentQuery.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getCrossrefWorksUseCase(currentQuery, _state.value.rows).fold(
                onSuccess = { resultList ->
                    _state.update {
                        it.copy(
                            works = resultList,
                            isLoading = false
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Error al obtener los datos de Crossref"
                        )
                    }
                }
            )
        }
    }
}
