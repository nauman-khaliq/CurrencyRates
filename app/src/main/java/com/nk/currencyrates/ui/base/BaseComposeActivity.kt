/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 *
 */

package com.nk.currencyrates.ui.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Abstract Activity which binds [ViewModel] [VM] and [ViewBinding] [VB]
 */
abstract class BaseComposeActivity<VM : ViewModel> : AppCompatActivity() {

    protected abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = ContextCompat.getColor(this, R.color.windowBackground)
//                ) {
//
//                }
                SetComposeUI()
            }
        }
    }

    @Composable
    protected abstract fun SetComposeUI()
}
