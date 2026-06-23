package com.garageinventory.ui.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.garageinventory.data.CarPartEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel = koinViewModel(),
    onAddClick: () -> Unit,
    onPartClick: (Int) -> Unit
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мой Гараж") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить деталь")
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { query ->
                    viewModel.handleIntent(InventoryIntent.Search(query))
                },
                label = { Text("Поиск по названию или OEM...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
            ) {
                if (state.parts.isEmpty()) {
                    item {
                        Text(
                            text = "Ничего не найдено",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(state.parts) { part ->
                        PartItem(
                            part = part,
                            onClick = { onPartClick(part.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PartItem(part: CarPartEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = part.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "OEM: ${part.oemNumber}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "В наличии: ${part.quantity} шт.", style = MaterialTheme.typography.bodySmall)
        }
    }
}