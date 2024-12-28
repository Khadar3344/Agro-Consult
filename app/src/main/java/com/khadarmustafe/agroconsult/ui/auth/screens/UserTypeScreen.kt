package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.CustomAppBar
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn

@Composable
fun UserTypeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomAppBar(
            onBackBtnClick = { navController.navigateUp() },
            appBarTitle = "User Type"
        )
        Spacer(modifier = Modifier.height(120.dp))
        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.new_farmer)
        ) { navController.navigate("new_farmer") }

        Spacer(modifier = Modifier.height(20.dp))

        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.already_farmer)
        ) { navController.navigate("already_farmer") }
    }
}