package com.example.netclan.data

import com.example.netclan.R

data class UserData(
    val name:String,
    val location:String,
    val role :String,
    val distance :String,
    val purpose : String,
    val status :String,
    val image : Int,
    val connectionType : String
)

val data = listOf(
    UserData(
        "Vasanth",
        "Razole",
        "Andriod Dev",
        "10Km",
        "Coffe | Business | Movies",
        "Hello There!",
        R.drawable.person1,
        "Individual"
    ),
    UserData(
        "Binod",
        "Hyderabad",
        "Web Dev",
        "12Km",
        "Coffe | Business | Movies",
        "Hello There!",
        R.drawable.person2,
        "Individual"
    ),
    UserData(
        "Sai",
        "Amalapuram",
        "Ios Dev",
        "15Km",
        "Coffe | Business | Movies",
        "Hello There!",
        R.drawable.person3,
        "Individual"
    ),
    UserData(
        "Ravi",
        "kakinada",
        "Production Manager",
        "20Km",
        "Coffe | Business | Movies",
        "Hello There!",
        R.drawable.person4,
        "Business"

    ),
    UserData(
        "Vishnu",
        "Guntur",
        "Consultant Office",
        "30Km",
        "Coffe | Business | Movies",
        "Hello There!",
        R.drawable.person5,
        "Merchant"
    )
)