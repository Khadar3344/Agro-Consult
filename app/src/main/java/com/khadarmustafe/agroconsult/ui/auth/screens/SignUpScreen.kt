package com.khadarmustafe.agroconsult.ui.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn
import com.khadarmustafe.agroconsult.components.CustomTextField
import com.khadarmustafe.agroconsult.components.ErrorSuggestion
import com.khadarmustafe.agroconsult.ui.theme.primaryLight


@Composable
fun SignUpScreen(navController: NavHostController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var error by remember { mutableStateOf("") }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val conPasswordErrorState = remember { mutableStateOf(false) }
    val nameErrorState = remember { mutableStateOf(false) }
    val phoneNumberErrorState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 34.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            trailingIcon = R.drawable.user,
            label = "Name",
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            errorState = nameErrorState,
            onChange = { newText ->
                name = newText
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            trailingIcon = R.drawable.phone,
            label = "Phone Number",
            keyboardType = KeyboardType.Phone,
            visualTransformation = VisualTransformation.None,
            errorState = phoneNumberErrorState,
            onChange = { newNumber ->
                phoneNumber = newNumber
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            trailingIcon = R.drawable.lock,
            label = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            errorState = passwordErrorState,
            onChange = { newPass ->
                password = newPass
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            trailingIcon = R.drawable.lock,
            label = "Confirm Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            errorState = conPasswordErrorState,
            onChange = { newPass ->
                confirmPassword = newPass
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        when {
            nameErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid name.")
            }

            phoneNumberErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid number.")
            }

            emailErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid email address.")
            }

            passwordErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid password.")
            }

            conPasswordErrorState.value -> {
                ErrorSuggestion(message = "Confirm Password miss matched.")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomDefaultBtn(
            shapeSize = 50f,
            btnText = stringResource(R.string.sign_up)
        ) {
            if (name.text.isNotEmpty() && phoneNumber.text.isNotEmpty() && password.text.isNotEmpty() && confirmPassword.text.isNotEmpty()) {
                navController.navigate("region_selection")
            } else if (password != confirmPassword) {
                error = "Passwords do not match"
            } else {
                error = "Please fill in all fields"
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { navController.navigate("sign_in") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already have an account? Sign In",
                color = primaryLight
            )
        }
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}
