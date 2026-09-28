package org.ucb.appp1.crossref.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.crossref.domain.model.WorkModel
import org.ucb.appp1.crossref.presentation.viewmodel.CrossrefEvent
import org.ucb.appp1.crossref.presentation.viewmodel.CrossrefState
import org.ucb.appp1.crossref.presentation.viewmodel.CrossrefViewModel

@Composable
fun CrossrefScreen(
    viewModel: CrossrefViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CrossrefHeader()
            Spacer(modifier = Modifier.height(12.dp))
            CrossrefSearchBar(
                query = state.query,
                isLoading = state.isLoading,
                onQueryChange = { viewModel.emitEvent(CrossrefEvent.OnQueryChange(it)) },
                onSearch = { viewModel.emitEvent(CrossrefEvent.OnSearch) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CrossrefContent(state = state)
        }
    }
}

@Composable
private fun CrossrefHeader() {
    Text(
        text = "Crossref - Búsqueda de Artículos",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun CrossrefSearchBar(
    query: String,
    isLoading: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Buscar artículo...") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        Button(
            onClick = onSearch,
            enabled = !isLoading && query.isNotBlank()
        ) {
            Text("Buscar")
        }
    }
}

@Composable
private fun CrossrefContent(state: CrossrefState) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.errorMessage != null -> {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        state.works.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontraron resultados",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.works) { work ->
                    WorkItemCard(work = work)
                }
            }
        }
    }
}

@Composable
fun WorkItemCard(work: WorkModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = work.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            WorkDetails(work = work)
            WorkUrlRow(url = work.url)
        }
    }
}

@Composable
private fun WorkDetails(work: WorkModel) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        WorkInfoRow(label = "Autor(es):", value = work.authors)
        WorkInfoRow(label = "Fecha de publicación:", value = work.publishedDate)
        WorkInfoRow(label = "Revista / Publicación:", value = work.containerTitle)
        WorkInfoRow(label = "DOI:", value = work.doi)
        WorkInfoRow(label = "Tipo de publicación:", value = work.type)
    }
}

@Composable
private fun WorkUrlRow(url: String) {
    if (url.isBlank()) return
    val uriHandler = LocalUriHandler.current

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "URL: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = url,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            textDecoration = TextDecoration.Underline,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable {
                runCatching { uriHandler.openUri(url) }
            }
        )
    }
}

@Composable
private fun WorkInfoRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
