package com.garageinventory.ui.inventory

import com.garageinventory.data.CarPartEntity

data class InventoryState(
    val parts: List<CarPartEntity> = emptyList(),
    val searchQuery: String = ""
)

sealed class InventoryIntent {
    object LoadInventory : InventoryIntent()
    data class Search(val query: String) : InventoryIntent()
    data class DeletePart(val part: CarPartEntity) : InventoryIntent()
    data class SavePart(val part: CarPartEntity) : InventoryIntent()
}