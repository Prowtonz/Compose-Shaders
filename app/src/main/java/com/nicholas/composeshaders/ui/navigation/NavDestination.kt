package com.nicholas.composeshaders.ui.navigation

sealed class NavDestination(val route: String) {

    object ShaderPreview : NavDestination("shaderPreview")

    object ShaderDemo : NavDestination("shaderDemo") {

        const val routeWithArg: String = "shaderDemo/{shaderId}"

        const val arg0: String = "shaderId"

    }

}