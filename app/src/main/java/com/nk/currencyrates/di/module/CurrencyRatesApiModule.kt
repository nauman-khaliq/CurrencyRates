/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 *
 */

package com.nk.currencyrates.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.nk.currencyrates.data.remote.api.CurrencyRatesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * [CurrencyRatesApiModule] that will handle api related dependencies.
 * [CurrencyRatesAppModule] is included in this module
 */
@InstallIn(SingletonComponent::class)
@Module(includes = [CurrencyRatesAppModule::class])
class CurrencyRatesApiModule {

    /**
     * Provides retrofit service with Moshi converter and okhttp client
     * @param okHttpClient of type [OkHttpClient]
     * @return [CurrencyRatesService]
     */
    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): CurrencyRatesService = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .baseUrl(CurrencyRatesService.We1_API_URL)
        .client(okHttpClient)
        .build()
        .create(CurrencyRatesService::class.java)
}
