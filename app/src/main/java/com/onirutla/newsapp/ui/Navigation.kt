package com.onirutla.newsapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.onirutla.newsapp.ui.screens.Screens
import com.onirutla.newsapp.ui.screens.home.HomeScreen
import com.onirutla.newsapp.ui.screens.home.HomeScreenUiEvent
import com.onirutla.newsapp.ui.screens.home.HomeScreenViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(
            route = Screens.HomeScreen.route,
            arguments = listOf(),
            deepLinks = listOf()
        ) { backStackEntry ->
            val vm: HomeScreenViewModel = hiltViewModel()
            val state by vm.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit, block = { vm.invoke() })

            HomeScreen(
                modifier = Modifier,
                state = state,
                onEvent = vm::onEvent,
                onUiEvent = {
                    when (it) {
                        is HomeScreenUiEvent.OnItemClick -> {

                        }
                    }
                },
            )
        }
    }
}