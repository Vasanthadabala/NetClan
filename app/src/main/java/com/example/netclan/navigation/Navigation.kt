package com.example.netclan.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.netclan.screens.ExploreScreen
import com.example.netclan.screens.RefineScreen

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation()
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Explore.route)
    {
        composable(Explore.route)
        {
            ExploreScreen(navController)
        }
        composable(Refine.route)
        {
            RefineScreen(navController)
        }
    }
}
