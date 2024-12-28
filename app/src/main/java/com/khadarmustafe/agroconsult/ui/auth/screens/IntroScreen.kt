package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn

@Composable
fun IntroScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.agro_consult),
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Agro Consult connects farmers with experts consultants to provide " +
                    "tailored advice and solutions for improved " +
                    "agricultural productivity",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(120.dp))
        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.sign_in)
        ) { navController.navigate("sign_in") }

        Spacer(modifier = Modifier.height(20.dp))

        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.sign_up)
        ) { navController.navigate("sign_up") }

    }
}