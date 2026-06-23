package com.garageinventory

import com.garageinventory.data.CarPartEntity
import org.junit.Assert.assertTrue
import org.junit.Test

class InventoryViewModelTest {

    @Test
    fun testPartCreation() {
        val part = CarPartEntity(
            id = 1,
            name = "Масло BMW",
            oemNumber = "12345",
            quantity = 1,
            category = "Жидкость"
        )

        // Проверяем, что данные сохранились верно
        assertTrue(part.name == "Масло BMW")
        assertTrue(part.category == "Жидкость")
    }
}