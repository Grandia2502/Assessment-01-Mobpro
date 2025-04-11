package com.grandiamuhammad3096.assessment01.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("konversiSuhuScreen")
    data object About: Screen("aboutScreen")
}