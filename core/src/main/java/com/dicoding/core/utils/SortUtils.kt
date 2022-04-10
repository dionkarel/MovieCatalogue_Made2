package com.dicoding.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val BEST_VOTE = "Best"
    const val WORST_VOTE = "Worst"
    const val MOVIE_ENTITY = "movie_entities"

    fun getSortQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            BEST_VOTE -> simpleQuery.append("ORDER By voteAverage DESC")
            WORST_VOTE -> simpleQuery.append("ORDER By voteAverage ASC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}