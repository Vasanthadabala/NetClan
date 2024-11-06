package com.example.netclan.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.netclan.data.availabilityOptions
import com.example.netclan.data.purposeCategories
import com.example.netclan.navigation.Explore
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun RefineScreen(navController:NavHostController)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Refine", fontWeight = FontWeight.Bold, color = Color.White)},
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Explore.route) {
                        popUpTo(Explore.route)
                        {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(30.dp),
                            tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xff0C2D48))
            )
        }
    ){
        RefineScreenContent()
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun RefineScreenContent() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var imei by remember { mutableStateOf("") }
    var tagName by remember { mutableStateOf("") }
    var registrationNumber by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }

    val options = listOf("Option 1", "Option 2", "Option 3") // Replace with your API data

    var selectedPurpose by rememberSaveable { mutableStateOf<Set<String>>(loadSelectedPurpose(context))}
    var sliderPosition by rememberSaveable { mutableFloatStateOf(loadSliderPosition(context)) }
    var selectedAvailability by rememberSaveable { mutableStateOf(loadSelectedAvailability(context)) }
    var statusText by rememberSaveable { mutableStateOf(loadStatusText(context)) }
    val maxTextLength = 150
    var isDropdownMenuVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(top = 70.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Select Your Availability",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                color = Color(0xff0C2D48),
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            )
            Box {
                OutlinedTextField(
                    value = selectedAvailability,
                    onValueChange = { selectedAvailability = it },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        color = Color(0xff0C2D48)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                        .border(1.5.dp, Color(0xff0C2D48), shape = RoundedCornerShape(15)),
                    shape = RoundedCornerShape(15),
                    enabled = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Close the keyboard or perform any action on 'Done' pressed
                            isDropdownMenuVisible = false
                        }
                    ),
                    trailingIcon = {
                        // Show dropdown menu icon
                        IconButton(
                            onClick = { isDropdownMenuVisible = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xff0C2D48)
                            )
                        }
                    }
                )

                DropdownMenu(
                    expanded = isDropdownMenuVisible,
                    onDismissRequest = { isDropdownMenuVisible = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    availabilityOptions.forEach { options ->
                        androidx.compose.material.DropdownMenuItem(
                            onClick = {
                                selectedAvailability = options
                                isDropdownMenuVisible = false
                            }) {
                            Text(
                                text = options,
                                color = Color(0xff0C2D48),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Text(
                text = "Add Your Status",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                color = Color(0xff0C2D48),
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            )
            Column {
                OutlinedTextField(
                    value = statusText,
                    onValueChange = {
                        if (it.length <= maxTextLength) {
                            statusText = it
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    textStyle = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        color = Color(0xff0C2D48)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                        .border(1.5.dp, Color(0xff0C2D48), shape = RoundedCornerShape(15)),
                    shape = RoundedCornerShape(15)
                )
                Text(
                    text = "${statusText.length}/150",
                    fontSize = 18.sp,
                    color = Color(0xff0C2D48),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 330.dp)
                )
            }
            Text(
                text = "Select Hyper Local Distance",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                color = Color(0xff0C2D48),
                modifier = Modifier.padding(top = 25.dp, start = 20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "${sliderPosition.roundToInt()}",
                    fontSize = 20.sp,
                    color = Color(0xff0C2D48),
                    fontWeight = FontWeight.Bold
                )
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 1f..100f,
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xff0C2D48),
                        thumbColor = Color(0xff0C2D48)
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "1Km",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff0C2D48),
                    modifier = Modifier.padding(start = 20.dp, end = 100.dp)
                )
                Text(
                    text = "100Km",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff0C2D48),
                    modifier = Modifier.padding(start = 165.dp)
                )
            }
            Text(
                text = "Select Purpose",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                color = Color(0xff0C2D48),
                modifier = Modifier.padding(top = 25.dp, start = 20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    purposeCategories.take(3).forEach { purpose ->
                        PurposeButton(purpose = purpose, selectedPurpose, onPurposeSelected = {
                            if (it in selectedPurpose) {
                                selectedPurpose -= it
                            } else {
                                selectedPurpose += it
                            }
                        })
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    purposeCategories.takeLast(3).forEach { purpose ->
                        PurposeButton(
                            purpose = purpose,
                            selectedPurpose = selectedPurpose,
                            onPurposeSelected = {
                                if (it in selectedPurpose) {
                                    selectedPurpose -= it
                                } else {
                                    selectedPurpose += it
                                }
                            })
                    }
                }
            }
        }

        Button(
            onClick = {
                if(selectedPurpose.isNotEmpty()){
                    saveSelectedAvailability(context,selectedAvailability)
                    saveSliderPosition(context,sliderPosition)
                    saveSelectedPurpose(context,selectedPurpose)
                    saveStatusText(context, statusText)
                }else{
                    Toast.makeText(
                        context,
                        "Select atleast one category in purpose",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xff0C2D48)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            shape = RoundedCornerShape(26)
        ) {
            Text(
                text = "Save&Explore",
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun PurposeButton(purpose:String,selectedPurpose:Set<String>,onPurposeSelected:(String)->Unit){
    val isSelected = purpose in selectedPurpose
    Button(
        colors = if (isSelected) ButtonDefaults.buttonColors(Color(0xff0C2D48)) else ButtonDefaults.buttonColors(Color.White),
        onClick = { onPurposeSelected(purpose) },
        modifier = Modifier.padding(5.dp),
        border = BorderStroke(1.5.dp, color = Color(0xff0C2D48))
    ) {
        Text(text = purpose,
            color =  if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
//For Saving dropdown menu
fun saveSelectedAvailability(context: Context, selectedAvailability: String) {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("selected", selectedAvailability)
    editor.apply()
    Toast.makeText(context, "Settings are Saved", Toast.LENGTH_LONG).show()
}
fun loadSelectedAvailability(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    return sharedPreferences.getString("selected", "Available") ?: "Available"
}

// Function to save the status text to shared preferences
fun saveStatusText(context: Context, text: String) {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("statusText", text)
    editor.apply()
}
fun loadStatusText(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    return sharedPreferences.getString("statusText", "") ?: ""
}

//For saving slider position
fun saveSliderPosition(context: Context, sliderPosition: Float) {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putFloat("sliderPosition", sliderPosition)
    editor.apply()
}
fun loadSliderPosition(context: Context): Float {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    return sharedPreferences.getFloat("sliderPosition", 1.0f)
}

//For saving PurposeCategory
fun saveSelectedPurpose(context: Context, selectedPurpose: Set<String>) {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putStringSet("selectedPurpose", selectedPurpose)
    editor.apply()
}

fun loadSelectedPurpose(context: Context): Set<String> {
    val sharedPreferences = context.getSharedPreferences("My_Pre", Context.MODE_PRIVATE)
    return sharedPreferences.getStringSet("selectedPurpose", setOf()) ?: setOf()
}