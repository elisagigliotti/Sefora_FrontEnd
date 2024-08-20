package it.unical.informatica.ea.sefora_frontend

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import it.unical.informatica.ea.sefora_frontend.activity.LoginActivity
import it.unical.informatica.ea.sefora_frontend.ui.theme.Sefora_FrontEndTheme
import it.unical.informatica.ea.sefora_frontend.viewmodel.LoginViewModel
import java.io.FileNotFoundException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sefora_FrontEndTheme {
                SeforaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeforaApp(
    loginViewModel: LoginViewModel = LoginViewModel()
) {
    // index delle pagine
    val selectedIndex = remember { mutableIntStateOf(0) }
    // variabile che controlla l'apertura e chiusura della login bottomsheet
    val showLogin = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "S  E  F  O  R  A",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },
        bottomBar = {
            BottomAppBar{
                NavigationBar{
                    //Home
                    NavigationBarItem(
                        selected = selectedIndex.value == 0,
                        onClick = { selectedIndex.value = 0 },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Home, contentDescription = "Home")
                                Text("Home")
                            }
                        })

                    //Ricerca
                    NavigationBarItem(
                        selected = selectedIndex.value == 1,
                        onClick = { selectedIndex.value = 1 },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                                Text("Search")
                            }
                        })

                    //Wishlist
                    NavigationBarItem(
                        selected = selectedIndex.value == 2,
                        onClick = { selectedIndex.value = 2 },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.FavoriteBorder, contentDescription = "Wishlist")
                                Text("Wishlist")
                            }
                        })

                    //Utente
                    NavigationBarItem(
                        selected = selectedIndex.value == 3,
                        onClick = { selectedIndex.value = 3 },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Person, contentDescription = "Account")
                                Text("Account")
                            }
                        })
                }
            }
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if(selectedIndex.value == 0) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text =
                    """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.
                """.trimIndent(),
                )
            }
            if(selectedIndex.value == 1) {}
            if(selectedIndex.value == 2) {}
            if(selectedIndex.value == 3) {
                showLogin.value = true;
                LoginActivity(
                    viewModel = loginViewModel,
                    onDismissRequest = {
                        showLogin.value = false
                        selectedIndex.value = 0
                    },
                    onLoginSuccess = {

                    },
                    onRegisterSuccess = {

                    },
                )
            }
        }
    }
}

@Composable
fun decodeUriAsBitmap(uri: Uri?): Bitmap? {
    val context: Context = LocalContext.current
    var bitmap: Bitmap? = null
    bitmap = try {
        BitmapFactory.decodeStream(
            context
                .contentResolver.openInputStream(uri!!)
        )
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }
    return bitmap
}

@Composable
fun showAlert(title: String, description: String, onDialogClosed: () -> Unit) {
    AlertDialog(onDismissRequest = {
        onDialogClosed()
    },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        }, confirmButton = {
            Button(
                onClick = {
                    onDialogClosed()
                }) {
                Text("Close")
            }
        }
    )
}