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

package com.nk.currencyrates.ui.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nk.currencyrates.data.repository.DataFrom
import com.nk.currencyrates.data.repository.HomeRepository
import com.nk.currencyrates.model.State
import com.nk.currencyrates.model.response.currency.CurrenciesData
import com.nk.currencyrates.model.response.currency.CurrencyDataUI
import com.nk.currencyrates.model.response.user.User
import com.nk.currencyrates.ui.main.home.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [MainActivity]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) :
    ViewModel() {

    private val _users: MutableStateFlow<State<List<User>>> = MutableStateFlow(State.loading())
    val users: StateFlow<State<List<User>>> = _users

    private val _currenciesData: MutableStateFlow<State<List<CurrencyDataUI>?>> = MutableStateFlow(State.loading())
    val currenciesData: StateFlow<State<List<CurrencyDataUI>?>> = _currenciesData

    private var allUsers = listOf<User>()

    /**
     * Gets random users list using [HomeRepository] mapping on [State] and and passing data to _orders [StateFlow]
     */
    fun getRandomUsers() {
        _users.value = State.loading()
        viewModelScope.launch {
            homeRepository.getRandomUsers()
                .map {
                    State.fromResource(it)
                }
                .collectLatest {
                    _users.value = generateUsersList(it)
                }
        }
    }

    /**
     * Gets currencies data using [HomeRepository] mapping on [State] and and passing data to _orders [StateFlow]
     */
    fun getCurrenciesData() {
        _currenciesData.value = State.loading()
        viewModelScope.launch {
            homeRepository.getCurrenciesData()
                .map {
                    State.fromResource(it)
                }
                .collectLatest {
                    _currenciesData.value = getRefinedCurrencyData(it)
                }
        }
    }


    /**
     * Refines Currency data to show in the ui
     * @param state pass State<CurrenciesData?>
     * @return State<List<CurrencyDataUI>?>
     */
    private fun getRefinedCurrencyData(state: State<CurrenciesData?>): State<List<CurrencyDataUI>?> {
        return when(state) {
            is State.Loading -> {
                State.loading()
            }
            is State.Success -> {
                val currencyList = ArrayList<CurrencyDataUI>()
                state.data?.currenciesRates?.forEach {
                    currencyList.add(CurrencyDataUI(it.key, it.value))
                }
                State.success(currencyList, state.dataFrom)
            }
            is State.Error -> {
                State.error(state.message)
            }
        }
    }
    /**
     * Filter users list with respect to current location
     */
    fun updateUsersList() {
        _users.value = State.success(filterAndSortUsers(allUsers), DataFrom.CACHED)
        //_users.value = generateUsersList(users.value)
    }

    /**
     * Generates users list to show
     * @param users of type [State<List<User>>]
     */
    private fun generateUsersList(users: State<List<User>>): State<List<User>> {
        return when (users) {
            is State.Loading -> {
                State.loading()
            }
            is State.Success -> {
                allUsers = users.data
                val sortedUsers = filterAndSortUsers(allUsers)
                State.success(sortedUsers, users.dataFrom)
            }
            is State.Error -> {
                State.error(users.message)
            }
        }
    }

    /**
     * Filters users list for 2000KM and sort users list in ascending distance order
     * @param users of type [List<User>]
     * @return List<User>
     */
    private fun filterAndSortUsers(users: List<User>): List<User> {
        val currentLoc = HomeFragment.currentLocation
        val filtered = users.filter {
            it.distance = calculateDistance(it, currentLoc).toString()
            (it.distance?.toInt() ?: 0) <= (1000 * 2000)
        }
        return filtered.sortedBy { it.distance?.toInt() ?: 0 }
    }

    /**
     * Calculates distance between user location and provided [currentLoc] location
     * @param user of type [User]
     * @param currentLoc of type [Location]
     * @return distance in meters of type [Int]
     */
    private fun calculateDistance(user: User, currentLoc: Location): Int {
        val userLoc = Location("")
        userLoc.latitude = user.location?.coordinates?.latitude?.toDouble() ?: (0).toDouble()
        userLoc.longitude = user.location?.coordinates?.longitude?.toDouble() ?: (0).toDouble()
        return currentLoc.distanceTo(userLoc).toInt()
    }
}


