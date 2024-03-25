package com.example.character_datasource.cache

import com.example.character_datasource.cache.model.toPhotoDto
import com.example.character_datasource.network.model.PhotoDto

class CharactersCacheImpl(
    charactersDb: CharactersDatabase
) : CharactersLocal {

    private val queries = charactersDb.photoDbQueries

    override suspend fun getPhotoDtoList(): List<PhotoDto> {
        return queries.getPhotos().executeAsList().map {
            it.toPhotoDto()
        }
    }

    override suspend fun insertPhotoDto(character: PhotoDto) {
        return character.run {
            queries.insertPhoto(
                albumId = albumId?.toLong() ?: 0L,
                id = id?.toLong() ?: 0L,
                thumbnailUrl = thumbnailUrl ?: "",
                title = title ?: "",
                url = url ?: ""
            )
        }
    }

    override suspend fun insertPhotoDtoList(characters: List<PhotoDto>) {
        characters.forEach {
            try {
                insertPhotoDto(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getPhotoDto(id: Int): PhotoDto {
        return queries.getPhoto(id.toLong()).executeAsOne().toPhotoDto()
    }
}