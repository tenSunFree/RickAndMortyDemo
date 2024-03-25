package com.example.rickandmortydemo.ui.navigation

sealed class Screen(val route: String) {

    data object CharactersList : Screen(
        route = "charactersList"
    )
}
