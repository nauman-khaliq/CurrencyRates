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

package com.nk.currencyrates.data.remote.api

import com.nk.currencyrates.data.remote.api.CurrencyRatesService.Companion.We1_API_URL
import com.nk.currencyrates.model.response.BaseResponse
import com.nk.currencyrates.model.response.currency.CurrenciesData
import com.nk.currencyrates.model.response.user.User
import retrofit2.Response
import retrofit2.http.GET

/**
 * Service to fetch We1 data from server [We1_API_URL].
 */
interface CurrencyRatesService {


    /**
     * Post request for getting order items for merged view.
     */

    @GET("/api?results=300&exc=login,registered,dob,phone,id,gender")
    suspend fun getRandomUsers(): Response<BaseResponse<List<User>>>

    /**
     * Post request for getting order items for merged view.
     */

    @GET("/v1/latest?access_key=1a10c6a164534737356b1fe17e4eb08a")
    suspend fun getCurrenciesData(): Response<CurrenciesData?>

    companion object {
        /**
         * Base url.
         */
        const val We1_API_URL = "http://api.exchangeratesapi.io"
    }
}
