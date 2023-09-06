package com.nk.currencyrates.ui.main

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nk.currencyrates.R
import com.nk.currencyrates.model.State
import com.nk.currencyrates.model.response.currency.CurrencyDataUI
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Home Screen UI
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val currenciesState = mainViewModel.currenciesData.collectAsStateWithLifecycle()
    Log.i("HomeScreen", "${currenciesState.value}")
    when (currenciesState.value) {
        is State.Loading -> {
            LinearProgressIndicator()
        }
        is State.Success -> {
            val currenciesDataList = (currenciesState.value as State.Success<List<CurrencyDataUI>?>).data?.toList()
            currenciesDataList?.let { ShowCurrencyList("EUR", currencyDataList = it) }
        }
        else -> {

        }
    }

}

/**
 * Draws base currency info layout
 * @param baseCurrency pass [String]
 */
@Composable
fun ShowBaseCurrencyInfo(baseCurrency: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Base Currency", color = colorResource(id = R.color.black50), style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Text(
                text = baseCurrency, style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Rate",  color = colorResource(id = R.color.black50), style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "1.0",
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

/**
 * Draws currency rates list using lazy column
 * @param baseCurrency pass [String]
 * @param currencyDataList pass [List<CurrencyDataUI>]
 */
@Composable
fun ShowCurrencyList(baseCurrency: String, currencyDataList: List<CurrencyDataUI>) {
    Column {
        ShowBaseCurrencyInfo(baseCurrency)
        LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
            items(items = currencyDataList) {
                CurrencyListItem(currencyData = it)
            }
        }
    }
}

/**
 * Previews the UI of Home Screen
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ShowCurrencyList("EUR",
            listOf(
                CurrencyDataUI("AED", 122.3f),
                CurrencyDataUI("AED", 122.3f),
                CurrencyDataUI("AED", 122.3f)
            )
        )
    }
}
