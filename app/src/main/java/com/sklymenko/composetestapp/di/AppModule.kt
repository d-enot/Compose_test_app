package com.sklymenko.composetestapp.di

import com.sklymenko.composetestapp.BuildConfig
import com.sklymenko.composetestapp.api.Api
import com.sklymenko.composetestapp.source.MovieDataSource
import com.sklymenko.composetestapp.source.MovieLocalDataSource
import com.sklymenko.composetestapp.source.MovieRemoteDataSource
import com.sklymenko.composetestapp.source.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideApi(): Api =
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Api::class.java)

    @Provides
    @Remote
    @Singleton
    fun provideRemoteDataSource(apiKey: String, api: Api): MovieDataSource {
        return MovieRemoteDataSource(apiKey, api)
    }

    @Provides
    @Local
    @Singleton
    fun provideLocalDataSource(): MovieDataSource {
        return MovieLocalDataSource()
    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepoModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieRepository(
        @Local localDataSource: MovieDataSource,
        @Remote remoteDataSource: MovieDataSource
    ): MovieRepository {
        return MovieRepository(remoteDataSource, localDataSource)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Local

