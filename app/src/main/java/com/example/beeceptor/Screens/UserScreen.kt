package com.example.beeceptor.Screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.anilcomposeex.hiltMVVMCOMPOSE.viewmodels.UserViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@Composable
fun NetworkScreen(navController: NavController) {
    UserListScreen(navController)
}


@Composable
fun UserListScreen(navController: NavController) {
    val connectionState by rememberConnectivityState()

    val isConnected = remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }
    if( isConnected.value) {
        val userViewModel: UserViewModel = hiltViewModel()
        val userdata = userViewModel.userItem.collectAsState()
        if (userdata.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading", style = MaterialTheme.typography.headlineMedium)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                userdata.value?.let { results ->
                    items(results.size) { index ->
                        UserItemScreenCard(userItem = results[index], navController)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center){
            Text(text = "Please check your InternetConnection", textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun UserItemScreenCard(userItem: UserItem, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().clickable{
            Toast.makeText(context,"I am "+userItem.name, Toast.LENGTH_LONG).show()
            navController.navigate(userItem)
        },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            userItem.photo?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Descriptive text for the image",
                    modifier = Modifier.size(80.dp)
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(16.dp)), // Adjust as needed
                )
            }

            Column(
                modifier = Modifier.weight(1f) // Text takes remaining space
            ) {
                Text(
                    text =  userItem.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = userItem.email,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = userItem.phone,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
sealed interface NetworkConnectionState {
    data object Available : NetworkConnectionState
    data object Unavailable : NetworkConnectionState
}

private fun networkCallback(callback: (NetworkConnectionState) -> Unit): ConnectivityManager.NetworkCallback =
    object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(NetworkConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(NetworkConnectionState.Unavailable)
        }
    }

fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): NetworkConnectionState {
    val network = connectivityManager.activeNetwork

    val isConnected = connectivityManager
        .getNetworkCapabilities(network)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

    return if (isConnected) NetworkConnectionState.Available else NetworkConnectionState.Unavailable
}

fun Context.observeConnectivityAsFlow(): Flow<NetworkConnectionState> = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState ->
        trySend(connectionState)
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

val Context.currentConnectivityState: NetworkConnectionState
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getCurrentConnectivityState(connectivityManager)
    }

@Composable
fun rememberConnectivityState(): State<NetworkConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect {
            value = it
        }
    }
}
