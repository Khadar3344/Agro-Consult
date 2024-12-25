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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.CustomTextField
import com.khadarmustafe.agroconsult.components.ErrorSuggestion


@Composable
fun SignInScreen(/*navController: NavHostController*/) {
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
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.sign_in), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
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
        Button(
            onClick = {
                if (name.text.isEmpty() || password.text.isEmpty() || confirmPassword.text.isEmpty()) {
                    error = "Please fill in all fields"
                } else if (password != confirmPassword) {
                    error = "Passwords do not match"
                } else {
                    error = "Sign Up Successful!" // Placeholder logic
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { /*navController.navigate("signin")*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't has account? Sign Up")
        }
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SignInScreen()
}