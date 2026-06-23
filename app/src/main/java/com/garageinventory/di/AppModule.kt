package com.garageinventory.di

import androidx.room.Room
import com.garageinventory.data.CarPartRepository
import com.garageinventory.data.GarageDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

import com.garageinventory.ui.inventory.InventoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GarageDatabase::class.java,
            "garage_database"
        ).build()
    }

    single { get<GarageDatabase>().carPartDao() }
    single { CarPartRepository(get()) }

    viewModel { InventoryViewModel(get()) }
}