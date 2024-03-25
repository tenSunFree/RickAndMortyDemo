package com.example.character_datasource.cache

import com.example.character_datasource.cache.characterdatasource.newInstance
import com.example.character_datasource.cache.characterdatasource.schema
import com.example.characterdatasource.cache.PhotoDbQueries
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface CharactersDatabase : Transacter {
  public val photoDbQueries: PhotoDbQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = CharactersDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): CharactersDatabase =
        CharactersDatabase::class.newInstance(driver)
  }
}
