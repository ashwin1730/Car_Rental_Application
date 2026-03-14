package com.ltimindtree.carrentalapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ltimindtree.carrentalapplication.model.Car
import com.ltimindtree.carrentalapplication.model.User
import com.ltimindtree.carrentalapplication.repository.CarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository = CarRepository()) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _allCars = MutableStateFlow<List<Car>>(emptyList())
    
    val categories = listOf("All", "Sedan", "SUV", "Electric", "Sports", "Luxury")

    val cars: StateFlow<List<Car>> = combine(_allCars, _searchQuery, _selectedCategory) { cars, query, category ->
        cars.filter { car ->
            val matchesQuery = car.brand.contains(query, ignoreCase = true) || 
                               car.model.contains(query, ignoreCase = true)
            val matchesCategory = category == "All" || car.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _bookedCars = MutableStateFlow<List<Car>>(emptyList())
    val bookedCars: StateFlow<List<Car>> = _bookedCars.asStateFlow()

    private val _isBooking = MutableStateFlow(false)
    val isBooking: StateFlow<Boolean> = _isBooking.asStateFlow()

    private val _bookingEvent = MutableSharedFlow<String>()
    val bookingEvent: SharedFlow<String> = _bookingEvent.asSharedFlow()

    // User State
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        loadCars()
    }

    private fun loadCars() {
        _allCars.value = repository.getCars()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun bookCar(car: Car) {
        viewModelScope.launch {
            _isBooking.value = true
            delay(1500)
            
            _allCars.value = _allCars.value.map {
                if (it.id == car.id) it.copy(isAvailable = false) else it
            }
            
            if (!_bookedCars.value.any { it.id == car.id }) {
                _bookedCars.value = _bookedCars.value + car.copy(isAvailable = false)
            }
            
            _isBooking.value = false
            _bookingEvent.emit("Successfully booked ${car.brand} ${car.model}!")
        }
    }

    fun cancelBooking(car: Car) {
        viewModelScope.launch {
            _allCars.value = _allCars.value.map {
                if (it.id == car.id) it.copy(isAvailable = true) else it
            }
            _bookedCars.value = _bookedCars.value.filter { it.id != car.id }
            _bookingEvent.emit("Booking for ${car.brand} ${car.model} cancelled.")
        }
    }

    fun addCar(brand: String, model: String, price: Double, category: String, transmission: String, fuelType: String, seats: Int) {
        val newCar = Car(
            id = (_allCars.value.maxOfOrNull { it.id } ?: 0) + 1,
            brand = brand,
            model = model,
            pricePerDay = price,
            category = category,
            transmission = transmission,
            fuelType = fuelType,
            seats = seats
        )
        _allCars.value = _allCars.value + newCar
        viewModelScope.launch {
            _bookingEvent.emit("New car ${brand} ${model} added to catalog!")
        }
    }

    // User Methods
    fun registerUser(name: String, email: String, phone: String) {
        _currentUser.value = User(name, email, phone)
        viewModelScope.launch {
            _bookingEvent.emit("Welcome, $name!")
        }
    }

    fun logout() {
        _currentUser.value = null
    }
}
