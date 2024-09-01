package it.unical.informatica.ea.sefora_frontend.activity


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.viewmodel.AddProductViewModel
import it.unical.informatica.ea.sefora_frontend.models.ProductDto.Category

@Composable
fun AddProductActivity(
    onDismiss: () -> Unit,
) {
    val viewModel: AddProductViewModel = hiltViewModel()
    val productState by viewModel.productState.collectAsState()
    val showError = remember { mutableStateOf(false) }
    val showSuccess = remember { mutableStateOf(false) }

    val imageProduct = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        viewModel.onImageProductChanged(it)
    }

    val expanded = remember { mutableStateOf(false) }

    val nameError = viewModel.nameError
    val categoryError = viewModel.categoryError
    val descriptionError = viewModel.descriptionError
    val priceError = viewModel.priceError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Product",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = productState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text("Product Name") },
            isError = nameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        if (nameError != null) {
            Text(text = nameError, color = Color.Red)
        }

        // Use a Button instead of TextField to trigger Dropdown
        Button(
            onClick = { expanded.value = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = productState.category?.value ?: "Select Category")
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            Category.entries.forEach { categoryItem ->
                DropdownMenuItem(
                    text = { Text(categoryItem.value) },
                    onClick = {
                        viewModel.onCategoryChanged(categoryItem)
                        expanded.value = false
                    }
                )
            }
        }
        if (categoryError != null) {
            Text(text = categoryError, color = Color.Red)
        }

        OutlinedTextField(
            value = productState.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { Text("Description") },
            isError = descriptionError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        if (descriptionError != null) {
            Text(text = descriptionError, color = Color.Red)
        }

        OutlinedTextField(
            value = productState.price.toString(),
            onValueChange = { viewModel.onPriceChanged(it.toFloatOrNull() ?: 0f) },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = priceError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        if (priceError != null) {
            Text(text = priceError, color = Color.Red)
        }

        // Image Preview and Button
        if (productState.imageProduct != null) {
            Image(
                bitmap = productState.imageProduct!!.asImageBitmap(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(100.dp))
        }

        Button(
            onClick = { imageProduct.launch() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Add Image")
        }

        Button(
            onClick = {
                if(nameError != null || categoryError != null || descriptionError != null || priceError != null) {
                    return@Button
                }
                viewModel.createProduct(
                    name = productState.name,
                    category = productState.category!!,
                    description = productState.description,
                    price = productState.price,
                    imageProduct = productState.imageProduct,
                    onSuccess = { showSuccess.value = true },
                    onFailure = { showError.value = true }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Add Product")
        }

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go back")
        }
    }

    if (showSuccess.value) {
        showAlert("Done!", "Product added successfully!") {
            showSuccess.value = false
        }
    }

    if (showError.value) {
        showAlert("Server error", "Failed to add product, please retry later.") {
            showError.value = false
        }
    }
}


