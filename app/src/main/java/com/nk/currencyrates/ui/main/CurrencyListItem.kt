package com.nk.currencyrates.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nk.currencyrates.R
import com.nk.currencyrates.model.response.currency.CurrencyDataUI

/**
 * Draws Currency list item ui
 * @param currencyData pass [CurrencyDataUI]
 */
@Composable
fun CurrencyListItem(currencyData: CurrencyDataUI) {
    ShowCurrencyCard(currencyData = currencyData)
}
/**
 * Draws Currency Currency card with in list item of currency ui
 * @param currencyData pass [CurrencyDataUI]
 */
@Composable
private fun ShowCurrencyCard(currencyData: CurrencyDataUI) {
    Card(
        backgroundColor = colorResource(id = R.color.light_gray),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
    ) {
        CardContent(currencyData)
    }
}
/**
 * Draws Currency cards with content
 * @param currencyData pass [CurrencyDataUI]
 */
@Composable
private fun CardContent(currencyData: CurrencyDataUI) {
    Row(
        modifier = Modifier
            .padding(12.dp)
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
                .padding(12.dp)
        ) {
            Text(
                text = "Currency", color = colorResource(id = R.color.black50), style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.Normal
                ),
            )
            Text(
                text = currencyData.currency ?: "", style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(
                text = "Rate",  color = colorResource(id = R.color.black50), style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = currencyData.rate.toString() ?: "",
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}