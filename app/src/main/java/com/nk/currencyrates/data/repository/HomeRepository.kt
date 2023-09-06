/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 *
 */

package com.nk.currencyrates.data.repository

import com.nk.currencyrates.data.local.dao.CurrenciesDao
import com.nk.currencyrates.data.local.dao.UserDao
import com.nk.currencyrates.data.remote.api.CurrencyRatesService
import com.nk.currencyrates.model.response.BaseResponse
import com.nk.currencyrates.model.response.currency.CurrenciesData
import com.nk.currencyrates.model.response.user.User
import com.nk.currencyrates.utils.PreferenceHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface HomeRepository {
    /**
     * Fetches random users list from either server or database
     * @return Flow<Resource<List<User>>>
     */
    fun getRandomUsers(): Flow<Resource<List<User>>>

    /**
     * Fetches currencies data from either server or database
     * @return  Flow<Resource<CurrenciesData>>
     */
    fun getCurrenciesData(): Flow<Resource<CurrenciesData?>>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
class DefaultHomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val currenciesDao: CurrenciesDao,
    private val preferenceHelper: PreferenceHelper,
    private val currencyRatesService: CurrencyRatesService
) : HomeRepository {

    override fun getRandomUsers(): Flow<Resource<List<User>>> {
        return object : NetworkAndDbBoundRepository<List<User>, BaseResponse<List<User>>>() {
            override suspend fun saveRemoteData(response: BaseResponse<List<User>>) {
                response.results?.let {
                    userDao.deleteAllUsers()
                    userDao.addUsers(it)
                }
            }
            override fun fetchFromLocal(): List<User> = userDao.getAllUsers()
            override suspend fun fetchFromRemote(): Response<BaseResponse<List<User>>> =
                currencyRatesService.getRandomUsers()
        }.asFlow()
    }

    override fun getCurrenciesData(): Flow<Resource<CurrenciesData?>> {
        return object : NetworkAndDbBoundRepository<CurrenciesData?, CurrenciesData?>() {
            override suspend fun saveRemoteData(response: CurrenciesData?) {
                response?.let {
                    currenciesDao.deleteAllCurrenciesData()
                    currenciesDao.addCurrenciesData(listOf(response))
                }
            }
            override fun fetchFromLocal(): CurrenciesData? {
                val listCurrData = currenciesDao.getAllCurrenciesData()
                return if (listCurrData.isNotEmpty()) {
                    listCurrData[0]
                } else
                    null
            }
            override suspend fun fetchFromRemote(): Response<CurrenciesData?> =
                currencyRatesService.getCurrenciesData()
        }.asFlow()
    }

}
