package com.example.beeceptor.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.beeceptor.R

@Composable
fun DetailsScreen(userItem:UserItem) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState) // Apply the verticalScroll modifier
        .padding(15.dp)) {
        userItem.photo?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "User image",
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
        } ?: run {
            Text("Loading image...")
        }


        //Name
        Text(
            text = stringResource(R.string.username)+" "+userItem.name,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        //Company
        Text(
            //text = stringResource(g) userItem.company,
            text = stringResource(R.string.company)+" "+userItem.company,

            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        //email
        Text(
            text = stringResource(R.string.email)+" "+userItem.email,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        //address
        Text(
            text = stringResource(R.string.address)+" "+userItem.address,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
//state

        Text(
            text = stringResource(R.string.state)+" "+userItem.state,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        //country
        Text(
            text = stringResource(R.string.country)+" "+userItem.country,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        //phone
        Text(
            text = stringResource(R.string.phone)+" "+userItem.phone,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
    }

}

@Composable
fun detailsItem() {

    Box (modifier = Modifier.padding(4.dp)
        .size(16.dp)
        ){
        Text(text = " hari",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp,20.dp))
    }
}

/*@Composable
fun UserItemScreenCard(userItem: UserItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().clickable{
            Toast.makeText(context,"I am "+userItem.name, Toast.LENGTH_LONG).show()
        },
        shape = MaterialTheme.shapes.medium // Use MaterialTheme's shape for consistency
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
                    modifier = Modifier.size(80.dp), // Adjust as needed
                )
            } ?: run {
                // Display a placeholder or loading indicator while waiting for the URL
                Text("Loading image...")
            }

            Column(
                modifier = Modifier.weight(1f) // Text takes remaining space
            ) {
                Text(
                    text =  userItem.name,
                    style = MaterialTheme.typography.bodyMedium // Use MaterialTheme's typography
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
}*/
