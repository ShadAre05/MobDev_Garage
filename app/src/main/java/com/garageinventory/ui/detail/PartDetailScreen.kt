package com.garageinventory.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.garageinventory.data.CarPartEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartDetailScreen(
    part: CarPartEntity,
    onBackClick: () -> Unit,
    onDeleteClick: (CarPartEntity) -> Unit,
    onEditClick: (CarPartEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Информация") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditClick(part) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                    }
                    IconButton(onClick = { onDeleteClick(part) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Удалить")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = part.name, style = MaterialTheme.typography.headlineMedium)
            HorizontalDivider()
            Text(text = "Категория: ${part.category}", style = MaterialTheme.typography.titleMedium)
            Text(text = "OEM-номер: ${part.oemNumber}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "В наличии: ${part.quantity} шт.", style = MaterialTheme.typography.bodyLarge)
        }
    }
}