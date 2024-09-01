package it.unical.informatica.ea.sefora_frontend.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.models.AccountShortDto
import it.unical.informatica.ea.sefora_frontend.viewmodel.AccountViewModel
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminActivity(
    onDismissRequest: () -> Unit
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val showSuccessAlert = remember { mutableStateOf(false) }
    val adminState by viewModel.adminState.collectAsState()
    val sheetstate = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getAllAccounts()
    }

    ModalBottomSheet(
        sheetState = sheetstate,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Admin Activity")
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(adminState.accounts) { account ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(account.email)
                                Text(account.role!!.toString())
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(onClick = {
                                    viewModel.deleteAccount(account.id) {
                                        showSuccessAlert.value = true
                                    }
                                }) {
                                    Text("Delete")
                                }
                                if(account.role!! == AccountShortDto.Role.USER) {
                                    Button(onClick = {
                                        viewModel.makeAdmin(account.id) {
                                            showSuccessAlert.value = true
                                        }
                                    }) {
                                        Text("Promote")
                                    }
                                } else {
                                    Button(onClick = {
                                        viewModel.revokeAdmin(account.id) {
                                            showSuccessAlert.value = true
                                        }
                                    }) {
                                        Text("Demote")
                                    }
                                }

                                if(account.isBanned!!) {
                                    Button(onClick = {
                                        viewModel.unbanAccount(account.id) {
                                            showSuccessAlert.value = true
                                        }
                                    }) {
                                        Text("Unban")
                                    }
                                } else {
                                    Button(onClick = {
                                        viewModel.banAccount(account.id) {
                                            showSuccessAlert.value = true
                                        }
                                    }) {
                                        Text("Ban")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = onDismissRequest) {
            Text("Close")
        }
    }

    if(showSuccessAlert.value) {
        showAlert("Done!", "Action executed correctly") {
            viewModel.getAllAccounts()
            showSuccessAlert.value = false
        }
    }
}