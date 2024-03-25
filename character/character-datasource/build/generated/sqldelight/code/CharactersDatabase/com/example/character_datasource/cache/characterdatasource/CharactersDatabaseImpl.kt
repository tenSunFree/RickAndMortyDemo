package com.example.character_datasource.cache.characterdatasource

import com.example.character_datasource.cache.CharactersDatabase
import com.example.characterdatasource.cache.PhotoDbQueries
import com.example.characterdatasource.cache.PhotoEntity
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.`internal`.copyOnWriteList
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Unit
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<CharactersDatabase>.schema: SqlDriver.Schema
  get() = CharactersDatabaseImpl.Schema

internal fun KClass<CharactersDatabase>.newInstance(driver: SqlDriver): CharactersDatabase =
    CharactersDatabaseImpl(driver)

private class CharactersDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), CharactersDatabase {
  public override val photoDbQueries: PhotoDbQueriesImpl = PhotoDbQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE PhotoEntity(
          |    albumId INTEGER,
          |    id INTEGER NOT NULL PRIMARY KEY,
          |    thumbnailUrl TEXT,
          |    title TEXT,
          |    url TEXT
          |)
          """.trimMargin(), 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class PhotoDbQueriesImpl(
  private val database: CharactersDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), PhotoDbQueries {
  internal val getPhotos: MutableList<Query<*>> = copyOnWriteList()

  internal val getPhoto: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> getPhotos(mapper: (
    albumId: Long?,
    id: Long,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ) -> T): Query<T> = Query(-1977834718, getPhotos, driver, "PhotoDb.sq", "getPhotos",
      "SELECT * FROM PhotoEntity") { cursor ->
    mapper(
      cursor.getLong(0),
      cursor.getLong(1)!!,
      cursor.getString(2),
      cursor.getString(3),
      cursor.getString(4)
    )
  }

  public override fun getPhotos(): Query<PhotoEntity> = getPhotos { albumId, id, thumbnailUrl,
      title, url ->
    PhotoEntity(
      albumId,
      id,
      thumbnailUrl,
      title,
      url
    )
  }

  public override fun <T : Any> getPhoto(id: Long, mapper: (
    albumId: Long?,
    id: Long,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ) -> T): Query<T> = GetPhotoQuery(id) { cursor ->
    mapper(
      cursor.getLong(0),
      cursor.getLong(1)!!,
      cursor.getString(2),
      cursor.getString(3),
      cursor.getString(4)
    )
  }

  public override fun getPhoto(id: Long): Query<PhotoEntity> = getPhoto(id) { albumId, id_,
      thumbnailUrl, title, url ->
    PhotoEntity(
      albumId,
      id_,
      thumbnailUrl,
      title,
      url
    )
  }

  public override fun insertPhoto(
    albumId: Long?,
    id: Long?,
    thumbnailUrl: String?,
    title: String?,
    url: String?
  ): Unit {
    driver.execute(1050349540, """
    |INSERT OR REPLACE INTO PhotoEntity (albumId, id, thumbnailUrl, title, url)
    |VALUES (?,?,?,?,?)
    """.trimMargin(), 5) {
      bindLong(1, albumId)
      bindLong(2, id)
      bindString(3, thumbnailUrl)
      bindString(4, title)
      bindString(5, url)
    }
    notifyQueries(1050349540, {database.photoDbQueries.getPhotos +
        database.photoDbQueries.getPhoto})
  }

  private inner class GetPhotoQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T
  ) : Query<T>(getPhoto, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(628935537,
        """SELECT * FROM PhotoEntity WHERE id = ?""", 1) {
      bindLong(1, id)
    }

    public override fun toString(): String = "PhotoDb.sq:getPhoto"
  }
}
