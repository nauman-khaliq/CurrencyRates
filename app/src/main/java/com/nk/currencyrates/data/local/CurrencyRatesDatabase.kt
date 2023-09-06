/*
 * MIT License
 *
 * Copyright (c) 2020 Nauman Khaliq
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

package com.nk.currencyrates.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nk.currencyrates.data.local.dao.CurrenciesDao
import com.nk.currencyrates.data.local.dao.UserDao
import com.nk.currencyrates.model.response.currency.CurrenciesData
import com.nk.currencyrates.model.response.currency.RatesListJsonConverter
import com.nk.currencyrates.model.response.user.User

/**
 * Abstract We1 database.
 * It provides DAO [UserDao] by using method [getUserDao].
 */
@Database(
    entities = [User::class, CurrenciesData::class],
    version = DatabaseMigrations.DB_VERSION
)
@TypeConverters(RatesListJsonConverter::class)
abstract class CurrencyRatesDatabase : RoomDatabase() {
    /**
     * @return [UserDao] User Data Access Object.
     */
    abstract fun getUserDao(): UserDao

    /**
     * @return [CurrenciesDao] Currencies Data Access Object.
     */
    abstract fun getCurrenciesDao(): CurrenciesDao

    companion object {
        const val DB_NAME = "we1_database"

        @Volatile
        private var INSTANCE: CurrencyRatesDatabase? = null

        /**
         * @return database[CurrencyRatesDatabase] instance.
         */
        fun getInstance(context: Context): CurrencyRatesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyRatesDatabase::class.java,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
