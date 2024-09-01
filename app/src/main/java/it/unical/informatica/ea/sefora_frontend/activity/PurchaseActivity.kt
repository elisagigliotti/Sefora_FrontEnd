package it.unical.informatica.ea.sefora_frontend.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.viewmodel.PurchaseViewModel
import kotlinx.coroutines.Dispatchers


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseActivity(
    onDismiss: () -> Unit
) {
    val viewModel: PurchaseViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isLoading by viewModel.isLoading

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getPurchases()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            Surface {
                if (viewModel.purchases.value.purchases.isEmpty()) {
                    Text(
                        text = "No purchases found",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(viewModel.purchases.value.purchases) { purchase ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = "Purchase ID: ${purchase.id}")
                                Text(text = "Purchase total: ${purchase.totalPurchasePrice}€")
                                Text(text = "Purchase date: ${purchase.purchaseDate}")
                                Text(text = "Products:")

                                purchase.products.forEach { product ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = product.id.toString(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = product.name,
                                            modifier = Modifier.weight(2f)
                                        )
                                        Text(
                                            text = "${product.price}€",
                                            modifier = Modifier.weight(1f)
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
