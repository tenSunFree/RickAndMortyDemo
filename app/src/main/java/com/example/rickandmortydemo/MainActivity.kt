package com.example.rickandmortydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.rickandmortydemo.ui.navigation.Screen
import com.example.ui_character_list.ui.CharactersListScreen
import com.example.ui_character_list.ui.CharactersListViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModularizedRickAndMortyAppTheme {
                val navController = rememberNavController()
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.icon_top_bar),
                        contentDescription = ""
                    )
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CharactersList.route,
                        modifier = Modifier.weight(1f)
                    ) {
                        charactersListScreen()
                    }
                    Image(
                        painter = painterResource(id = R.drawable.icon_bottom_bar),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.charactersListScreen() {
    composable(
        route = Screen.CharactersList.route
    ) {
        val viewModel: CharactersListViewModel = getViewModel()
        CharactersListScreen(
            state = viewModel.state,
            navigateToDetailScreen = { },
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}