package com.example.character_datasource.network.model

import com.example.character_domain.PhotoModel
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val albumId: Int? = 0,
    val id: Int? = 0,
    val thumbnailUrl: String? = "",
    val title: String? = "",
    val url: String? = ""
)

fun PhotoDto.toPhotoModel() = PhotoModel(
    albumId = albumId,
    id = id,
    thumbnailUrl = thumbnailUrl,
    title = title,
    url = url
)