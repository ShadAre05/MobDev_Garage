package com.garageinventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarPartDao {
    @Query("SELECT * FROM garage_inventory")
    fun getAllParts(): Flow<List<CarPartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPart(part: CarPartEntity)

    @Delete
    suspend fun deletePart(part: CarPartEntity)

    @Query("SELECT * FROM garage_inventory WHERE name LIKE '%' || :searchQuery || '%' OR oemNumber LIKE '%' || :searchQuery || '%'")
    fun searchParts(searchQuery: String): Flow<List<CarPartEntity>>
}