package it.unical.informatica.ea.sefora_frontend.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.models.ProductDto
import it.unical.informatica.ea.sefora_frontend.viewmodel.AccountViewModel
import it.unical.informatica.ea.sefora_frontend.viewmodel.PurchaseViewModel
import it.unical.informatica.ea.sefora_frontend.viewmodel.WishlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductActivity(
    productDto: ProductDto,
    onDismissRequest: () -> Unit
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val wishlistViewModel: WishlistViewModel = hiltViewModel()
    val purchaseViewModel: PurchaseViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSuccessAlert = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            productDto.imageProduct?.let {
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
                text = productDto.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Product Category
            Text(
                text = "Category: ${productDto.category.value}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Product Description
            Text(
                text = productDto.description ?: "No description available",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Product Price
            productDto.price?.let { price ->
                Text(
                    text = "Price: $${price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add to wishlist button
            Button(
                onClick = {
                    wishlistViewModel.addProductToWishlist(productId = productDto.id!!) {
                        showSuccessAlert.value = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to wishlist")
            }

            Button(
                onClick = {
                     purchaseViewModel.convertProductToPurchase(productId = productDto.id!!) {
                        showSuccessAlert.value = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Buy now")
            }
            // Close Button
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Close")
            }
        }
    }

    if(showSuccessAlert.value) {
        showAlert("Done!", "Action executed successfully!") {
            showSuccessAlert.value = false
            onDismissRequest()
        }
    }
}