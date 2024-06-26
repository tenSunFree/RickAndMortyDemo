package com.example.character_interactors

import com.example.character_datasource.cache.CharactersLocal
import com.example.character_datasource.network.model.toPhotoModel
import com.example.character_domain.PhotoModel
import com.example.core.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterFromCache(private val cache: CharactersLocal) {

    operator fun invoke(id: Int): Flow<DataState<PhotoModel>> = flow {
        val cachedCharacter = cache.getPhotoDto(id)

        if (cachedCharacter != null) {
            emit(DataState.Success(data = cachedCharacter.toPhotoModel()))
        } else {
            throw Exception("Character with id $id doesn't exist in cache.")
        }
    }.safeDataStateFlow()
}