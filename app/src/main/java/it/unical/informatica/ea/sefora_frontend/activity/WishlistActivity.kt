package it.unical.informatica.ea.sefora_frontend.activity

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.models.WishlistDto
import it.unical.informatica.ea.sefora_frontend.viewmodel.WishlistViewModel
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun WishlistActivity(
    onDismiss: () -> Unit
) {
    val viewModel: WishlistViewModel = hiltViewModel()
    val wishlistState by viewModel.wishlistState.collectAsState()
    val showSingleWishlist: MutableState<Boolean> = remember { mutableStateOf(false) }
    val selectedWishlist: MutableState<WishlistDto?> = remember { mutableStateOf(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showWishlistUserDialog = remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()
    val error = viewModel.error
    val addUserState by viewModel.addUserState.collectAsState()

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getWishlistByCurrentUser()
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
    } else {
        if (viewModel.getLoggedInValue()) {
            showAlert("Login required!", "You need to login to access this page", onDismiss)
        } else if (error != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = error, color = Color.Red)
            }
        } else {
            Surface {
                if (wishlistState.wishlists.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No wishlists found")
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        OutlinedTextField(
                            value = addUserState.email,
                            onValueChange = { viewModel.onEmailChanged(it) },
                            label = { Text("Add user to wishlist") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            isError = viewModel.emailError != null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (viewModel.emailError != null) {
                            Text(text = viewModel.emailError!!, color = Color.Red)
                        }

                        Button(
                            onClick = {
                                viewModel.addAccountToWishlist(addUserState.email) {
                                    showWishlistUserDialog.value = true
                                }
                            }
                        ) {
                            Text("Add user")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(wishlistState.wishlists) { wishlist ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                                    onClick = {
                                        showSingleWishlist.value = true
                                        selectedWishlist.value = wishlist
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(10.dp)
                                            ) {
                                                Text(
                                                    text = "${wishlist.name}",
                                                    color = Color.Black
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                                if (!wishlist.products.isNullOrEmpty()) {
                                                    Text(
                                                        text = "Number of products: ${wishlist.products.size}",
                                                        color = Color.Black
                                                    )
                                                }
                                                Text(
                                                    text = "Owner: ${wishlist.account.email}",
                                                )
                                                Text(
                                                    text = "Whislis type: ${wishlist.type.value}",
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showSuccessDialog.value) {
            showAlert("Success", "Operation completed successfully") {
                viewModel.getWishlistByCurrentUser()
                showSuccessDialog.value = false
            }
        }

        if (showWishlistUserDialog.value) {
            showAlert("Done", "If the email is correct, we will update the wishlist.") {
                showWishlistUserDialog.value = false
            }
        }

        if (showSingleWishlist.value) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showSingleWishlist.value = false
                    selectedWishlist.value = null
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (selectedWishlist.value != null && !selectedWishlist.value!!.products.isNullOrEmpty()) {
                        items(selectedWishlist.value!!.products!!) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Product Image
                                    product.imageProduct?.let {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "Product Image",
                                            modifier = Modifier
                                                .size(150.dp)
                                                .padding(bottom = 16.dp)
                                        )
                                    }

                                    // Product Name
                                    Text(
                                        text = product.name,
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    // Product Category
                                    Text(
                                        text = "Category: ${product.category.value}",
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    // Product Price
                                    product.price?.let { price ->
                                        Text(
                                            text = "Price: $${price}",
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                    }

                                    if (selectedWishlist.value!!.account.id == viewModel.currentAccountId.value) {
                                        Button(
                                            onClick = {
                                                viewModel.removeProductFromWishlist(productId = product.id!!) {
                                                    showSuccessDialog.value = true
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = "Remove from wishlist"
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}