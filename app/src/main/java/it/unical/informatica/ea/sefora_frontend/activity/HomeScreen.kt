package it.unical.informatica.ea.sefora_frontend.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import it.unical.informatica.ea.sefora_frontend.models.ProductDto
import it.unical.informatica.ea.sefora_frontend.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val openArticolo: MutableState<Boolean> = remember { mutableStateOf(false) }
    val selectedArticolo: MutableState<ProductDto?> = remember { mutableStateOf(null) }

    val textStyle= androidx.compose.ui.text.TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

    val isLoading by viewModel.isLoading

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getArticoli()
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
        Surface {
            Column {
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Welcome to Sefora", style = textStyle)
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(viewModel.allProductsState.value.articoli) { articolo ->
                        Card(
                            onClick = {
                                openArticolo.value = true
                                selectedArticolo.value = articolo
                            },
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Column {
                                Image(
                                    bitmap = articolo.imageProduct!!.asImageBitmap(),
                                    contentDescription = "Image of product: ${articolo.name}",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                )
                                Row(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Column {
                                        Text(text = articolo.name)
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Column {
                                        Text(text = "${articolo.price}€")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if(openArticolo.value) {
        ProductActivity(selectedArticolo.value!!) {
            openArticolo.value = false
        }
    }
}