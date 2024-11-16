package com.nicholas.composeshaders.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nicholas.composeshaders.ui.navigation.NavDestination
import com.nicholas.composeshaders.ui.screens.shaderdemo.ShaderDemoScreen
import com.nicholas.composeshaders.ui.screens.shaderpreview.ShaderPreviewScreen

@Composable
fun ComposeShadersApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavDestination.ShaderPreview.route
    ) {
        composable(
            route = NavDestination.ShaderPreview.route,
            enterTransition = { slideInHorizontally(tween(200)) { 1500 } },
            exitTransition = { slideOutHorizontally(tween(200)) { -1500 } },
            popEnterTransition = { slideInHorizontally(tween(200)) { -1500 } },
            popExitTransition = { slideOutHorizontally(tween(200)) { 1500 } }
        ) {
            ShaderPreviewScreen {
                navController.navigate(NavDestination.ShaderDemo.route + '/' + it.id)
            }
        }
        composable(
            route = NavDestination.ShaderDemo.ROUTE_WITH_ARG,
            arguments = listOf(
                navArgument(NavDestination.ShaderDemo.ARG0) { type = NavType.IntType }
            ),
            enterTransition = { slideInHorizontally(tween(200)) { 1500 } },
            exitTransition = { slideOutHorizontally(tween(200)) { -1500 } },
            popEnterTransition = { slideInHorizontally(tween(200)) { -1500 } },
            popExitTransition = { slideOutHorizontally(tween(200)) { 1500 } }
        ) {
            val shaderId = it.arguments?.getInt(NavDestination.ShaderDemo.ARG0) ?: return@composable
            ShaderDemoScreen(shaderId, navController::navigateUp)
        }
    }
}