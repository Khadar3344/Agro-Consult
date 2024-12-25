package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn

@Composable
fun IntroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(120.dp))
        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.sign_in)
        ) { }

        Spacer(modifier = Modifier.height(20.dp))

        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.sign_up)
        ) { }

    }
}

@Preview(showBackground = true)
@Composable
private fun IntroScreenPreview() {
    IntroScreen()
}