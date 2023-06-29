package com.hyeokbeom.rxcassignment.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.rxcassignment.ui.anim.DotsFlashing
import com.hyeokbeom.rxcassignment.ui.theme.RXCAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var goodsList by remember { mutableStateOf(emptyList<GoodsListResponse.Good>()) }
            var isLoading by remember { mutableStateOf(false) }

            /** Main View 상태 관리 **/
            when (val it = viewModel.mainViewState.collectAsState().value) {
                is MainViewState.IsLoading -> isLoading = true
                // set goodsList and recomposing
                is MainViewState.MainList -> goodsList = it.list.also { isLoading = false }
                is MainViewState.Idle -> { /* TODO */ }
                is MainViewState.Error -> { /* TODO */ }
            }

            RXCAssignmentTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        /* Top Navigation */
                        NavigationArea()

                        /* Goods List */
                        GoodsArea(goodsList)
                    }

                    // Loading Progress
                    if (isLoading) DotsFlashing()
                }
            }
        }.also { viewModel.fetchGoodsList() }
    }
}