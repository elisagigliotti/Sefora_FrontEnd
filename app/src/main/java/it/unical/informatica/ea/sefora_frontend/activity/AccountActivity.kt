package it.unical.informatica.ea.sefora_frontend.activity

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.models.AccountDto
import it.unical.informatica.ea.sefora_frontend.viewmodel.AccountViewModel
import it.unical.informatica.ea.sefora_frontend.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AccountActivity(
    account: AccountDto,
    onDismissRequest: () -> Unit
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val accountState by viewModel.accountState.collectAsState()
    val updateProfile = remember { mutableStateOf(false) }
    val showSuccessAlert = remember { mutableStateOf(false) }
    val showAddProductActivity = remember { mutableStateOf(false) }
    val showAdminActivity = remember { mutableStateOf(false) }
    val showOrders = remember { mutableStateOf(false) }

    val profilePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        viewModel.onProfilePicChanged(it)
    }

    val isLoading by viewModel.isLoading
    val firstNameError = viewModel.firstNameError
    val lastNameError = viewModel.lastNameError
    val emailError = viewModel.emailError
    val phoneError = viewModel.phoneError

    LaunchedEffect(Dispatchers.IO) {
        accountState.firstName = account.firstname
        accountState.lastName = account.lastname
        accountState.email = account.email
        accountState.phone = account.phone ?: ""
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if(!showAddProductActivity.value || !showAdminActivity.value) {
        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Profile Image or Default Icon
            account.profileImage?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .padding(5.dp)
                )
            } ?: Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "Default Profile Picture",
                modifier = Modifier
                    .size(128.dp)
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            if (!updateProfile.value) {
                Text(
                    text = "Name: " + account.firstname
                )
                Text(
                    text = "Surname: " + account.lastname
                )
                Text(
                    text = "Email: " + account.email
                )
                Text(
                    text = "Phone: " + account.phone
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { updateProfile.value = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Update Profile")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            loginViewModel.logout()
                            onDismissRequest()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Logout")
                    }
                }
                Button(
                    onClick = { showOrders.value = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show orders")
                }

                if(account.role == AccountDto.Role.ADMIN) {
                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showAddProductActivity.value = true },
                        ) {
                            Text("Add Product")
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Button(
                            onClick = { showAdminActivity.value = true },
                        ) {
                            Text("Admin Panel")
                        }
                    }
                }

            } else {
                OutlinedTextField(
                    value = accountState.firstName.isBlank()
                        .let { if (it) account.firstname else accountState.firstName },
                    onValueChange = { viewModel.onFirstNameChanged(it) },
                    label = { Text("First Name") },
                    isError = firstNameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (firstNameError != null) {
                    Text(text = firstNameError, color = Color.Red)
                }

                OutlinedTextField(
                    value = accountState.lastName.isBlank()
                        .let { if (it) account.lastname else accountState.lastName },
                    onValueChange = { viewModel.onLastNameChanged(it) },
                    label = { Text("Last Name") },
                    isError = lastNameError != null,
                    modifier = Modifier.fillMaxWidth()
                )
                if (lastNameError != null) {
                    Text(text = lastNameError, color = Color.Red)
                }

                OutlinedTextField(
                    value = accountState.email.isBlank()
                        .let { if (it) account.email else accountState.email },
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Email") },
                    isError = emailError != null,
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError != null) {
                    Text(text = emailError, color = Color.Red)
                }

                OutlinedTextField(
                    value = accountState.phone.isBlank()
                        .let { if (it && account.phone != null) account.phone!! else accountState.phone },
                    onValueChange = { viewModel.onPhoneChanged(it) },
                    label = { Text("Phone") },
                    isError = phoneError != null,
                    modifier = Modifier.fillMaxWidth()
                )
                if (phoneError != null) {
                    Text(text = phoneError, color = Color.Red)
                }

                Button(
                    onClick = {
                        profilePicture.launch()
                        if(accountState.profilePic != null) {
                            viewModel.updateImage(
                                image = accountState.profilePic!!,
                                id = account.id!!,
                                firstName = account.firstname,
                                email = account.email
                            ) {
                                showSuccessAlert.value = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Upload Profile Picture")
                }

                Button(
                    onClick = {
                        viewModel.updateAccount(
                            id = account.id!!,
                            role = account.role,
                            banned = account.banned,
                            image = account.profileImage
                        ) { showSuccessAlert.value = true }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Account")
                }
                if (viewModel.error != null) {
                    Text(viewModel.error!!, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(5.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = accountState.oldPassword,
                    onValueChange = { viewModel.onOldPasswordChanged(it) },
                    label = { Text("Current password") },
                    isError = viewModel.oldPasswordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (viewModel.oldPasswordError != null) {
                    Text(text = viewModel.oldPasswordError!!, color = Color.Red)
                }

                OutlinedTextField(
                    value = accountState.newPassword,
                    onValueChange = { viewModel.onNewPasswordChanged(it) },
                    label = { Text("New password") },
                    isError = viewModel.newPasswordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (viewModel.newPasswordError != null) {
                    Text(text = viewModel.newPasswordError!!, color = Color.Red)
                }

                OutlinedTextField(
                    value = accountState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    label = { Text("Confirm password") },
                    isError = viewModel.confirmPasswordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (viewModel.confirmPasswordError != null) {
                    Text(text = viewModel.confirmPasswordError!!, color = Color.Red)
                }

                Button(
                    onClick = { viewModel.updatePassword(accountState.oldPassword, accountState.newPassword, accountState.confirmPassword) {
                        showSuccessAlert.value = true
                    } },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Password")
                }

                Button(
                    onClick = { updateProfile.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }

    if(showSuccessAlert.value) {
        showAlert("Done!", "Account updated successfully") {
            showSuccessAlert.value = false
            updateProfile.value = false
            account.phone = accountState.phone
            account.email = accountState.email
            account.firstname = accountState.firstName
            account.lastname = accountState.lastName
            account.profileImage = accountState.profilePic
            accountState.newPassword = ""
            accountState.oldPassword = ""
            accountState.confirmPassword = ""
        }
    }

    if(showAddProductActivity.value) {
        AddProductActivity {
            showAddProductActivity.value = false
        }
    }

    if(showAdminActivity.value) {
        AdminActivity {
            showAdminActivity.value = false
        }
    }

    if(showOrders.value) {
        PurchaseActivity {
            showOrders.value = false
        }
    }
}

