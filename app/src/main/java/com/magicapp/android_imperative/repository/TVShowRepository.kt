package com.magicapp.android_imperative.repository

import com.magicapp.android_imperative.db.TVShowDao
import com.magicapp.android_imperative.models.TVShow
import com.magicapp.android_imperative.network.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvShowService: TVShowService,
    private val tvShowDao: TVShowDao
) {

    /**
     * Retrofit Related
     */

    suspend fun apiTVShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)

    suspend fun apiTVShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)

    /**
     * Room Related
     */

    suspend fun getTVShowFromDB() = tvShowDao.getTVShowsFromDB()

    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)

    suspend fun deleteTVShowsFromDB() = tvShowDao.deleteTVShowsFromDB()

}