package com.garageinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.garageinventory.ui.add.AddPartScreen
import com.garageinventory.ui.detail.PartDetailScreen
import com.garageinventory.ui.inventory.InventoryScreen
import com.garageinventory.ui.theme.GarageInventoryfinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GarageInventoryfinalProjectTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "inventory") {

                    composable("inventory") {
                        val viewModel: com.garageinventory.ui.inventory.InventoryViewModel =
                            org.koin.androidx.compose.koinViewModel()
                        InventoryScreen(
                            viewModel = viewModel,
                            onAddClick = { navController.navigate("add_part") },
                            onPartClick = { partId -> navController.navigate("detail/$partId") }
                        )
                    }

                    composable("add_part") {
                        val viewModel: com.garageinventory.ui.inventory.InventoryViewModel =
                            org.koin.androidx.compose.koinViewModel()
                        AddPartScreen(
                            onBackClick = { navController.popBackStack() },
                            onSaveClick = { newPart ->
                                viewModel.handleIntent(
                                    com.garageinventory.ui.inventory.InventoryIntent.SavePart(
                                        newPart
                                    )
                                )
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = "detail/{partId}",
                        arguments = listOf(androidx.navigation.navArgument("partId") {
                            type = androidx.navigation.NavType.IntType
                        })
                    ) { backStackEntry ->
                        val partId = backStackEntry.arguments?.getInt("partId") ?: return@composable
                        val viewModel: com.garageinventory.ui.inventory.InventoryViewModel =
                            org.koin.androidx.compose.koinViewModel()
                        val state = viewModel.state.collectAsState().value

                        val part = state.parts.find { it.id == partId }

                        if (part != null) {
                            PartDetailScreen(
                                part = part,
                                onBackClick = { navController.popBackStack() },
                                onDeleteClick = { partToDelete ->
                                    viewModel.handleIntent(com.garageinventory.ui.inventory.InventoryIntent.DeletePart(partToDelete))
                                    navController.popBackStack()
                                },
                                onEditClick = { partToEdit ->
                                    navController.navigate("edit/${partToEdit.id}")
                                }
                            )
                        }
                    }

                    composable(
                        route = "edit/{partId}",
                        arguments = listOf(androidx.navigation.navArgument("partId") { type = androidx.navigation.NavType.IntType })
                    ) { backStackEntry ->
                        val partId = backStackEntry.arguments?.getInt("partId") ?: return@composable
                        val viewModel: com.garageinventory.ui.inventory.InventoryViewModel = org.koin.androidx.compose.koinViewModel()
                        val state = viewModel.state.collectAsState().value
                        val part = state.parts.find { it.id == partId }

                        if (part != null) {
                            com.garageinventory.ui.edit.EditPartScreen(
                                part = part,
                                onBackClick = { navController.popBackStack() },
                                onSaveClick = { updatedPart ->
                                    viewModel.handleIntent(com.garageinventory.ui.inventory.InventoryIntent.SavePart(updatedPart))
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}