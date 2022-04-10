package com.dicoding.core.data.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dicoding.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDAO {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getAllMovie(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateFavMovie(movies: MovieEntity)

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    fun getFavMovie(): Flow<List<MovieEntity>>

}