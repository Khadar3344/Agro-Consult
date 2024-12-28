package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.khadarmustafe.agroconsult.components.CustomAppBar
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn

@Composable
fun RegionSelectionScreen(navController: NavController) {
    val regions = listOf("Awdal", "Saaxil", "Maroodi-jeex", "Togdheer", "Sool", "Sanaag")
    val selectedRegion = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomAppBar(
            onBackBtnClick = { navController.navigateUp() },
            appBarTitle = "Region Selection"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Select your region:",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        regions.forEach { region ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (selectedRegion.value == region),
                    onClick = { selectedRegion.value = region }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = region,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = "Continue"
        ) { navController.navigate("user_type") }
    }
}