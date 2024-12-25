package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun NewFarmerScreen() {
    var selectedAmount by remember { mutableStateOf("") }
    var expandedAmount by remember { mutableStateOf(false) }

    var selectedFarmType by remember { mutableStateOf("") }
    var expandedFarmType by remember { mutableStateOf(false) }

    var selectedCity by remember { mutableStateOf("") }
    var expandedCity by remember { mutableStateOf(false) }

    val investmentAmounts = listOf("1000 - 5000", "6000 - 10000", "11000 - 15000", "16000", "20000")
    val farmTypes = listOf("Dairy", "Poultry", "Crop", "Fishery")
    val cities = listOf("Hargeisa", "Gabilay", "Salaxlay", "Arabsiyo")


}

@Composable
fun DropdownSelector(
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
        /*DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }*/
    }
}

