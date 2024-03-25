package com.example.character_datasource.network

import com.example.character_datasource.network.model.PhotoDto
import com.example.core.DataState

interface CharactersRemote {
    suspend fun getCharacters(page: Int): DataState<List<PhotoDto>>
}