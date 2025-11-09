package com.sooq.price.di

import android.content.Context
import androidx.room.Room
import com.sooq.price.data.PriceRepository
import com.sooq.price.data.local.AppDatabase
import com.sooq.price.data.local.PriceDao
import com.sooq.price.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sooq_price_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePriceDao(database: AppDatabase): PriceDao {
        return database.priceDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): ApiService {
        return ApiService(client)
    }

    @Provides
    @Singleton
    fun providePriceRepository(api: ApiService, dao: PriceDao): PriceRepository {
        return PriceRepository(api, dao)
    }
}