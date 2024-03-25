package com.example.character_datasource.network

import com.example.character_datasource.network.model.PhotoDto
import com.example.core.ApiResponse
import com.example.core.DataState
import com.example.core.safeRequest
import com.example.core.toApiException
import io.ktor.client.HttpClient
import io.ktor.client.request.url

class CharactersServiceImpl(
    private val httpClient: HttpClient
) : CharactersRemote {

    override suspend fun getCharacters(page: Int): DataState<List<PhotoDto>> {
        val response = httpClient.safeRequest<List<PhotoDto>> {
            url(CharacterEndpoints.GET_CHARACTERS)
        }
        return when (response) {
            is ApiResponse.Success -> {
                // val limitedData = response.data.take(20)
                val limitedData = response.data
                DataState.Success(data = limitedData)
            }
            is ApiResponse.Error -> DataState.Error(response.cause.toApiException())
        }
    }
}