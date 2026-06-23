package com.garageinventory.data

import kotlinx.coroutines.flow.Flow

class CarPartRepository(private val dao: CarPartDao) {
    fun getAllParts(): Flow<List<CarPartEntity>> = dao.getAllParts()

    fun searchParts(query: String): Flow<List<CarPartEntity>> = dao.searchParts(query)

    suspend fun addOrUpdatePart(part: CarPartEntity) = dao.insertPart(part)

    suspend fun deletePart(part: CarPartEntity) = dao.deletePart(part)
}