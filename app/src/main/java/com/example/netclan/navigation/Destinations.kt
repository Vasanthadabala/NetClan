package com.example.netclan.navigation

interface Destinations
{
    val route:String
}
object Explore: Destinations {
    override val route="Explore"
}
object Refine: Destinations {
    override val route="Refine"
}