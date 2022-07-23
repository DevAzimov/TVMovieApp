package com.magicapp.android_imperative.di

import android.app.Application
import com.magicapp.android_imperative.db.AppDatabase
import com.magicapp.android_imperative.db.TVShowDao
import com.magicapp.android_imperative.network.Server.IS_TESTER
import com.magicapp.android_imperative.network.Server.getDevelopment
import com.magicapp.android_imperative.network.Server.getProduction
import com.magicapp.android_imperative.network.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit Related
     */

    @Provides
    fun server(): String {
        if (IS_TESTER) return getDevelopment()
        return getProduction()
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVShowService {
        return retrofitClient().create(TVShowService::class.java)
    }

    /**
     * Room Related
     */

    @Provides
    @Singleton
    fun appDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase): TVShowDao {
        return appDatabase.getTVShowDao()
    }

}