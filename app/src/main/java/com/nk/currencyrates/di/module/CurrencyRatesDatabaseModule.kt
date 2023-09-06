/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 * Copyright (c) 2020 Shreyas Patil
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.nk.currencyrates.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.nk.currencyrates.data.local.CurrencyRatesDatabase
import com.nk.currencyrates.data.local.dao.CurrenciesDao
import javax.inject.Singleton

/**
 * [CurrencyRatesDatabaseModule] which will provide database related dependencies
 */
@InstallIn(SingletonComponent::class)
@Module
class CurrencyRatesDatabaseModule {

    /**
     * Provides [CurrencyRatesDatabase] dependency
     * @param application of type [Application]
     * @return [CurrencyRatesDatabase]
     */
    @Singleton
    @Provides
    fun provideDatabase(application: Application) = CurrencyRatesDatabase.getInstance(application)

    /**
     * Provides [UserDao] dependency
     * @param database of type [CurrencyRatesDatabase]
     * @return [UserDao]
     */
    @Singleton
    @Provides
    fun provideUserDao(database: CurrencyRatesDatabase) = database.getUserDao()

    /**
     * Provides [CurrenciesDao] dependency
     * @param database of type [CurrencyRatesDatabase]
     * @return [CurrenciesDao]
     */
    @Singleton
    @Provides
    fun provideCurrenciesDao(database: CurrencyRatesDatabase) = database.getCurrenciesDao()

}
