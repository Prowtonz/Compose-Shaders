package com.nicholas.composeshaders.ui.navigation

sealed class NavDestination(val route: String) {

    data object ShaderPreview : NavDestination("shaderPreview")

    data object ShaderDemo : NavDestination("shaderDemo") {

        const val ROUTE_WITH_ARG: String = "shaderDemo/{shaderId}"

        const val ARG0: String = "shaderId"

    }

}