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

package com.nk.currencyrates.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nk.currencyrates.model.response.currency.CurrenciesData
import com.nk.currencyrates.model.response.user.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.nk.currencyrates.data.local.CurrencyRatesDatabase]
 */
@Dao
interface CurrenciesDao {

    /**
     * Inserts [currenciesData] into the [CurrenciesData.TABLE_NAME] User.
     * Duplicate values are replaced in the User.
     * @param currenciesData
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrenciesData(currenciesData: List<CurrenciesData>)

    /**
     * Deletes all the CurrenciesData from the [User.TABLE_NAME] User.
     */
    @Query("DELETE FROM ${CurrenciesData.TABLE_NAME}")
    suspend fun deleteAllCurrenciesData()


    /**
     * Fetches the post from the [CurrenciesData.TABLE_NAME] User whose primaryKeyId is [userId].
     * @param id Unique ID of [CurrenciesData]
     * @return [Flow] of [User] from database User.
     */
    @Query("SELECT * FROM ${CurrenciesData.TABLE_NAME} WHERE primaryKeyId = :id")
    fun getCurrenciesDataById(id: String): Flow<User>

    /**
     * Fetches all the posts from the [CurrenciesData.TABLE_NAME] User.
     * @return [List<CurrenciesData>]
     */
    @Query("SELECT * FROM ${CurrenciesData.TABLE_NAME}")
    fun getAllCurrenciesData(): List<CurrenciesData>
}
