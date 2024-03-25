package com.example.character_datasource.cache.model

import com.example.character_datasource.network.model.PhotoDto
import com.example.characterdatasource.cache.PhotoEntity

fun PhotoEntity.toPhotoDto() = PhotoDto(
    albumId = albumId?.toInt(),
    id = id.toInt(),
    thumbnailUrl = thumbnailUrl,
    title = title,
    url = url
)