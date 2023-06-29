package com.hyeokbeom.rxcassignment.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.rxcassignment.ui.theme.RXCAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var goodsList by remember { mutableStateOf(emptyList<GoodsListResponse.Good>()) }

            /** Main View 상태 관리 **/
            when (val it = viewModel.mainViewState.collectAsState().value) {
                is MainViewState.Idle -> { /* TODO */ }
                is MainViewState.IsLoading -> { /* TODO */ }
                is MainViewState.Error -> { /* TODO */ }
                is MainViewState.MainList -> goodsList = it.list    // set goodsList and recomposing
            }

            RXCAssignmentTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    /* Top Navigation */
                    NavigationArea()

                    /* Goods List */
                    GoodsArea(goodsList)
                }
            }
        }.also { viewModel.fetchGoodsList() }
    }
}

