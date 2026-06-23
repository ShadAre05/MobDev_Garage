package com.garageinventory.ui.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.garageinventory.data.CarPartEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPartScreen(
    part: CarPartEntity,
    onBackClick: () -> Unit,
    onSaveClick: (CarPartEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var partName by remember { mutableStateOf(part.name) }
            var oemNumber by remember { mutableStateOf(part.oemNumber) }
            var quantity by remember { mutableStateOf(part.quantity.toString()) }
            var partType by remember { mutableStateOf(part.category) }

            OutlinedTextField(
                value = partName,
                onValueChange = { partName = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = oemNumber,
                onValueChange = { oemNumber = it },
                label = { Text("OEM-номер (Артикул)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Количество штук") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Тип объекта:", style = MaterialTheme.typography.titleMedium)

            Row {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = partType == "Запчасть", onClick = { partType = "Запчасть" })
                    Text("Запчасть")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = partType == "Жидкость", onClick = { partType = "Жидкость" })
                    Text("Жидкость")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val updatedPart = CarPartEntity(
                        id = part.id,
                        name = partName,
                        oemNumber = oemNumber,
                        quantity = quantity.toIntOrNull() ?: 1,
                        category = partType
                    )
                    onSaveClick(updatedPart)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = partName.isNotBlank()
            ) {
                Text("Обновить")
            }
        }
    }
}