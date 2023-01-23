package com.nicholas.composeshaders.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nicholas.composeshaders.ui.navigation.NavDestination
import com.nicholas.composeshaders.ui.screens.shaderdemo.ShaderDemoScreen
import com.nicholas.composeshaders.ui.screens.shaderpreview.ShaderPreviewScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ComposeShadersApp() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
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
            route = NavDestination.ShaderDemo.routeWithArg,
            arguments = listOf(
                navArgument(NavDestination.ShaderDemo.arg0) { type = NavType.IntType }
            ),
            enterTransition = { slideInHorizontally(tween(200)) { 1500 } },
            exitTransition = { slideOutHorizontally(tween(200)) { -1500 } },
            popEnterTransition = { slideInHorizontally(tween(200)) { -1500 } },
            popExitTransition = { slideOutHorizontally(tween(200)) { 1500 } }
        ) {
            val shaderId = it.arguments?.getInt(NavDestination.ShaderDemo.arg0) ?: return@composable
            ShaderDemoScreen(shaderId, navController::navigateUp)
        }
    }
}