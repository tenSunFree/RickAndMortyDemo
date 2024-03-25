package com.example.character_datasource.cache

import com.example.character_datasource.network.model.PhotoDto

interface CharactersLocal {

    suspend fun getPhotoDtoList(): List<PhotoDto>

    suspend fun insertPhotoDto(character: PhotoDto)

    suspend fun insertPhotoDtoList(characters: List<PhotoDto>)

    suspend fun getPhotoDto(id: Int): PhotoDto?

    companion object Factory {
        val schema = CharactersDatabase.Schema
        const val dbName = "characters.db"
    }
}