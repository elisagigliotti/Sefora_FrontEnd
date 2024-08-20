package it.unical.informatica.ea.sefora_frontend.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import it.unical.informatica.ea.sefora_frontend.viewmodel.LoginViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

// ... (other imports)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginActivity(
    viewModel: LoginViewModel,
    onDismissRequest: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(text = if (viewModel.isLoginMode) "Login" else "Register", style = MaterialTheme.typography.headlineSmall)

            if (!viewModel.isLoginMode) {
                InputTextField(
                    value = viewModel.firstName,
                    onValueChange = viewModel::onFirstNameChanged,
                    label = "Name",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    error = viewModel.firstNameError
                )
                InputTextField(
                    value = viewModel.lastName,
                    onValueChange = viewModel::onLastNameChanged,
                    label = "Surname",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    error = viewModel.lastNameError
                )
            }

            InputTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChanged,
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                error = viewModel.emailError
            )

            InputTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChanged,
                label = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                error = viewModel.passwordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )

            if (!viewModel.isLoginMode) {
                InputTextField(
                    value = viewModel.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChanged,
                    label = "Confirm Password",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    error = viewModel.confirmPasswordError,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (confirmPasswordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
            }

            // Error Message
            if (viewModel.error != null) {
                Text(viewModel.error!!, color = MaterialTheme.colorScheme.error)
            }

            // Login/Register Button
            Button(onClick = {
                if (viewModel.isLoginMode) viewModel.login(onLoginSuccess) else viewModel.register(onRegisterSuccess)
            }) {
                Text(if (viewModel.isLoginMode) "Login" else "Register")
            }

            // Switch Mode
            TextButton(onClick = viewModel::toggleMode) {
                Text(if (viewModel.isLoginMode) "Don't have an account? Register" else "Already have an account? Login")
            }
        }
    }
}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    error: String?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

fun performRegistration(viewModel: LoginViewModel, onRegisterSuccess: () -> Unit) {
    // Create a JSON object with the registration details.
    val jsonObject = JSONObject().apply {
        put("email", viewModel.email)
        put("password", viewModel.password)
    }

    // Define the media type for the JSON payload.
    val mediaType = "application/json; charset=utf-8".toMediaType()

    // Create the request body with the JSON object and the correct media type.
    val requestBody = jsonObject.toString().toRequestBody(mediaType)

    // Build the request with the URL and request body.
    val request = Request.Builder()
        .url("https://yourapi.com/register")  // Replace with your actual registration URL.
        .post(requestBody)
        .build()

    // Initialize OkHttp client.
    val client = OkHttpClient()

    // Asynchronously send the request.
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Handle request failure by updating the UI with the error message.
            viewModel.error = "Registration Failed: ${e.message}"
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle successful response or show an error based on the response.
            if (response.isSuccessful) {
                onRegisterSuccess()  // Trigger success callback.
            } else {
                viewModel.error = "Registration Failed: ${response.message}"
            }
        }
    })
}
