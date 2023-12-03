package com.onirutla.newsapp.ui.screens

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("/newsapp")
}
