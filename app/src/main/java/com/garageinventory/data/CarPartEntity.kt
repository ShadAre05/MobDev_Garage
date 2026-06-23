package com.garageinventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garage_inventory")
data class CarPartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val oemNumber: String,
    val quantity: Int,
    val category: String
)
