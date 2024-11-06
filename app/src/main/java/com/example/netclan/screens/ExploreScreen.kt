package com.example.netclan.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.netclan.R
import com.example.netclan.data.UserData
import com.example.netclan.data.connectionTypes
import com.example.netclan.data.data
import com.example.netclan.navigation.BottomBar
import com.example.netclan.navigation.TopBar

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ExploreScreen(navController: NavHostController)
{
    Scaffold(
        bottomBar = { BottomBar(navController = navController ) },
        topBar = { TopBar(navController = navController) }
    ) {
        Column(Modifier.padding(top = 50.dp, bottom = 80.dp)) {
            ExploreScreenContent()
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun ExploreScreenContent(){
    var searchText by rememberSaveable{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var selectedCategory by rememberSaveable { mutableStateOf(connectionTypes.first()) }

    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xff103b5e))
                .padding(top = 25.dp, bottom = 10.dp)
        ) {
            items(connectionTypes) { type ->
                ConnectionType(
                    types = type,
                    isSelected = selectedCategory == type,
                    onCategorySelected = { selectedCategory = type })
            }
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                singleLine = true,
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    color = Color(0xff0C2D48)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(25.dp)
                    )
                },
                placeholder = { Text(text = "Search") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                shape = RoundedCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(horizontal = 5.dp)
                    .border(1.dp, Color(0xff0C2D48), shape = RoundedCornerShape(25))
                    .height(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter",
                modifier = Modifier
                    .size(41.dp)
                    .padding(start = 10.dp, top = 10.dp)
            )
        }

        val filteredMenuItems = data.filter { item ->
            (selectedCategory.isEmpty() || item.connectionType == selectedCategory) &&
                    (searchText.isEmpty() || item.name.contains(searchText, ignoreCase = true))
        }
        Column(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .weight(1f)
        ) {
            LazyColumn{
                items(filteredMenuItems) { user ->
                    UserCard(user = user)
                }
            }
        }
        Spacer(modifier = Modifier.height(0.dp))
    }
}

@Composable
fun ConnectionType(types: String,isSelected:Boolean,onCategorySelected:()-> Unit) {
    Text(
        text = types,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = if(isSelected) Color.White else Color.Gray,
        modifier = Modifier.clickable { onCategorySelected()}
    )
}

@Composable
fun UserCard(user: UserData)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ){
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "+INVITE",
                    color = Color(0xff0C2D48),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10))
                        .align(Alignment.CenterVertically)
                ){
                    Image(
                        painter = painterResource(id = user.image),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Text(
                        text = user.name,
                        color = Color(0xff0C2D48),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        text = "${user.location} | ${user.role}",
                        color = Color(0xff0C2D48),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W900,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        text = "Within ${user.distance}",
                        color = Color(0xff0C2D48),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            Text(
                text = user.purpose,
                color = Color(0xff0C2D48),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = user.status,
                color = Color(0xff0C2D48),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}