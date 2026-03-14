package com.ltimindtree.carrentalapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ltimindtree.carrentalapplication.viewmodel.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(viewModel: CarViewModel, onBack: () -> Unit) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Sedan") }
    var transmission by remember { mutableStateOf("Automatic") }
    var fuelType by remember { mutableStateOf("Petrol") }
    var seats by remember { mutableStateOf("5") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add New Car", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            StyledTextField(value = brand, onValueChange = { brand = it }, label = "Brand (e.g. Toyota)")
            StyledTextField(value = model, onValueChange = { model = it }, label = "Model (e.g. Corolla)")
            StyledTextField(
                value = price, 
                onValueChange = { price = it }, 
                label = "Price per Day ($)", 
                keyboardType = KeyboardType.Number
            )

            Text("Category", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            CategoryDropdown(
                selected = category,
                options = listOf("Sedan", "SUV", "Electric", "Sports", "Luxury"),
                onSelect = { category = it }
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Transmission", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    CategoryDropdown(selected = transmission, options = listOf("Automatic", "Manual"), onSelect = { transmission = it })
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Seats", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    StyledTextField(value = seats, onValueChange = { seats = it }, label = "Seats", keyboardType = KeyboardType.Number)
                }
            }

            StyledTextField(value = fuelType, onValueChange = { fuelType = it }, label = "Fuel Type (e.g. Electric, Petrol)")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (brand.isNotBlank() && model.isNotBlank() && price.isNotBlank()) {
                        viewModel.addCar(
                            brand, model, price.toDoubleOrNull() ?: 0.0,
                            category, transmission, fuelType, seats.toIntOrNull() ?: 5
                        )
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Add Car to Catalog", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(selected: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
