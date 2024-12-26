package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlreadyFarmerScreen(modifier: Modifier = Modifier) {
    var selectedAmount by remember { mutableStateOf("") }

    var selectedFarmType by remember { mutableStateOf("") }

    var selectedCity by remember { mutableStateOf("") }

    val investedAmounts = listOf("1000 - 10000", "20000 - 50000", "10000 - 150000", "160000 - 200000", "210000 - 50000")
    val farmTypes = listOf("Dairy", "Poultry", "Crop", "Fishery")
    val cities = listOf("Hargeisa", "Gabilay", "Salaxlay", "Arabsiyo")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Select Investment Details",
            fontSize = 30.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        // Investment Amount Dropdown
        DropdownSelector(
            label = "Invested Amount",
            options = investedAmounts,
            selectedOption = selectedAmount,
            onOptionSelected = { selectedAmount = it }
        )

        // Farm Type Dropdown
        DropdownSelector(
            label = "Farm Type",
            options = farmTypes,
            selectedOption = selectedFarmType,
            onOptionSelected = { selectedFarmType = it }
        )

        // City Dropdown
        DropdownSelector(
            label = "City",
            options = cities,
            selectedOption = selectedCity,
            onOptionSelected = { selectedCity = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Handle form submission
        }) {
            Text(text = "Submit")
        }
    }

}

@Composable
private fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {Text(text = option)},
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}