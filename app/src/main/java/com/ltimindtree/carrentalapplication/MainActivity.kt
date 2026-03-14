package com.ltimindtree.carrentalapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltimindtree.carrentalapplication.ui.screens.*
import com.ltimindtree.carrentalapplication.ui.theme.CarRentalApplicationTheme
import com.ltimindtree.carrentalapplication.viewmodel.CarViewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object CarList : Screen("car_list", "Cars", Icons.Default.List)
    object MyBookings : Screen("my_bookings", "Bookings", Icons.Default.DateRange)
    object AddCar : Screen("add_car", "Add", Icons.Default.Add)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Register : Screen("register", "Register", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarRentalApplicationTheme {
                val navController = rememberNavController()
                val viewModel: CarViewModel = viewModel()
                val user by viewModel.currentUser.collectAsState()
                val cars by viewModel.cars.collectAsState()

                Scaffold(
                    bottomBar = {
                        if (user != null) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            
                            if (currentDestination?.route in listOf(Screen.CarList.route, Screen.MyBookings.route, Screen.AddCar.route, Screen.Profile.route)) {
                                NavigationBar {
                                    val items = listOf(Screen.CarList, Screen.MyBookings, Screen.AddCar, Screen.Profile)
                                    items.forEach { screen ->
                                        NavigationBarItem(
                                            icon = { Icon(screen.icon, contentDescription = null) },
                                            label = { Text(screen.label) },
                                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                            onClick = {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController, 
                        startDestination = if (user == null) Screen.Register.route else Screen.CarList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Register.route) {
                            RegistrationScreen(viewModel = viewModel)
                        }
                        composable(Screen.CarList.route) {
                            CarListScreen(
                                viewModel = viewModel,
                                onCarClick = { car ->
                                    navController.navigate("car_detail/${car.id}")
                                }
                            )
                        }
                        composable(Screen.MyBookings.route) {
                            MyBookingsScreen(viewModel = viewModel)
                        }
                        composable(Screen.AddCar.route) {
                            AddCarScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                        composable("car_detail/{carId}") { backStackEntry ->
                            val carId = backStackEntry.arguments?.getString("carId")?.toIntOrNull()
                            val car = cars.find { it.id == carId }
                            if (car != null) {
                                CarDetailScreen(
                                    car = car,
                                    viewModel = viewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
