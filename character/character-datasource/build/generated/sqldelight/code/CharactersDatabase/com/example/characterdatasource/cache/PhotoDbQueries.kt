package com.example.characterdatasource.cache

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String
import kotlin.Unit

public interface PhotoDbQueries : Transacter {
  public fun <T : Any> getPhotos(mapper: (
    albumId: Long?,
    id: Long,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ) -> T): Query<T>

  public fun getPhotos(): Query<PhotoEntity>

  public fun <T : Any> getPhoto(id: Long, mapper: (
    albumId: Long?,
    id: Long,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ) -> T): Query<T>

  public fun getPhoto(id: Long): Query<PhotoEntity>

  public fun insertPhoto(
    albumId: Long?,
    id: Long?,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ): Unit
}
