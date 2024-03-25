package com.example.character_interactors

import com.example.character_datasource.cache.CharactersLocal
import com.example.character_datasource.network.CharactersRemote
import com.example.character_datasource.network.model.PhotoDto
import com.example.character_datasource.network.model.toPhotoModel
import com.example.character_domain.PhotoModel
import com.example.core.DataState
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val remote: CharactersRemote,
    // private val service: CharactersService,
    private val local: CharactersLocal
    // private val cache: CharactersCache
) {

    operator fun invoke() = flow<DataState<List<PhotoModel>>> {
        val characters = mutableListOf<PhotoDto>()

        val dataState = getCharacters(remote)
        println("GetCharacters, invoke, dataState: $dataState")
        if (dataState is DataState.Success) {
            println("GetCharacters, invoke, dataState2: $dataState")
            characters.addAll(dataState.data)
        }

        println("GetCharacters, invoke, dataState3: $dataState")
        // local.insertCharacter(PhotoDto(1, 1, "thumbnailUrl", "title", "url"))
        // local.insertCharacter2(PhotoDto(1, 1, "thumbnailUrl", "title", "url"))

        local.insertPhotoDtoList(characters)
        println("GetCharacters, invoke, dataState4")
        val cachedCharacters = local.getPhotoDtoList()
        println("GetCharacters, invoke, dataState5, cachedCharacters: ${cachedCharacters.size}")
//
        if (cachedCharacters.isNotEmpty()) {
            emit(DataState.Success(data = cachedCharacters.map { it.toPhotoModel() }))
        } else {
            throw dataState.cause as Exception
        }
    }.safeDataStateFlow()

    /**
     * getCharacters() method returns 60 characters from API. This API automatically limits each API response to 20 characters only and since pagination is not in place yet, this method loads characters till page 3.
     * */
    private suspend fun getCharacters(service: CharactersRemote): DataState<List<PhotoDto>> {
        val characters = mutableListOf<PhotoDto>()
        val dataState = service.getCharacters(0)
        if (dataState is DataState.Success) {
            characters.addAll(dataState.data)
        } else if (dataState is DataState.Error) {
            return DataState.Error(dataState.cause)
        }
        return DataState.Success(data = characters)
    }
}