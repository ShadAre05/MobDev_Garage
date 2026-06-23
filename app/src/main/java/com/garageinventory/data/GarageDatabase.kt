package com.garageinventory.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CarPartEntity::class], version = 1, exportSchema = false)
abstract class GarageDatabase : RoomDatabase() {
    abstract fun carPartDao(): CarPartDao
}