package com.magicapp.android_imperative.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magicapp.android_imperative.models.TVShow

@Dao
interface TVShowDao {

    @Query("select * from tv_show")
    suspend fun getTVShowsFromDB() : List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShowToDB(tvShow: TVShow)

    @Query("delete from tv_show")
    suspend fun deleteTVShowsFromDB()
}