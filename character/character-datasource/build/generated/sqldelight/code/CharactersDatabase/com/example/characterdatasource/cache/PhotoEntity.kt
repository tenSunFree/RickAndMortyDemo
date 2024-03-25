package com.example.characterdatasource.cache

import kotlin.Long
import kotlin.String

public data class PhotoEntity(
  public val albumId: Long?,
  public val id: Long,
  public val thumbnailUrl: String?,
  public val title: String?,
  public val url: String?
) {
  public override fun toString(): String = """
  |PhotoEntity [
  |  albumId: $albumId
  |  id: $id
  |  thumbnailUrl: $thumbnailUrl
  |  title: $title
  |  url: $url
  |]
  """.trimMargin()
}
