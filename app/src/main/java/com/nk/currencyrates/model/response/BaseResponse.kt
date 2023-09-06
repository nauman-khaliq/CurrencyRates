/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 */
package com.nk.currencyrates.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(val results: T?)