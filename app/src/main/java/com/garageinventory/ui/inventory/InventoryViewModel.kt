package com.garageinventory.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garageinventory.data.CarPartEntity
import com.garageinventory.data.CarPartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: CarPartRepository) : ViewModel() {
    private val _state = MutableStateFlow(InventoryState())
    val state: StateFlow<InventoryState> = _state.asStateFlow()

    init {
        handleIntent(InventoryIntent.LoadInventory)
    }

    fun handleIntent(intent: InventoryIntent) {
        when (intent) {
            is InventoryIntent.LoadInventory -> loadParts()
            is InventoryIntent.Search -> {
                _state.update { it.copy(searchQuery = intent.query) }
                searchParts(intent.query)
            }
            is InventoryIntent.DeletePart -> deletePart(intent.part)
            is InventoryIntent.SavePart -> savePart(intent.part)
        }
    }

    private fun loadParts() {
        viewModelScope.launch {
            repository.getAllParts().collect { partsList ->
                _state.update { it.copy(parts = partsList) }
            }
        }
    }

    private fun searchParts(query: String) {
        viewModelScope.launch {
            repository.searchParts(query).collect { filteredList ->
                _state.update { it.copy(parts = filteredList) }
            }
        }
    }

    private fun deletePart(part: CarPartEntity) {
        viewModelScope.launch {
            repository.deletePart(part)
        }
    }

    private fun savePart(part: CarPartEntity) {
        viewModelScope.launch {
            repository.addOrUpdatePart(part)
        }
    }
}