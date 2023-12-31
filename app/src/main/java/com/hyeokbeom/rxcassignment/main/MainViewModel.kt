package com.hyeokbeom.rxcassignment.main

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.usecase.GoodLikeUseCase
import com.hyeokbeom.domain.usecase.GoodsListUseCase
import com.hyeokbeom.rxcassignment.ext.launchOnIO
import com.hyeokbeom.shared.executeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val goodsListUseCase: GoodsListUseCase,
    private val goodLikeUseCase: GoodLikeUseCase,
) : ViewModel(), MainViewIntent {
    val mainViewState = MutableStateFlow<MainViewState>(MainViewState.Idle)

    override fun fetchGoodsList() = launchOnIO {
        mainViewState.value = MainViewState.IsLoading
        delay(3000) // Loading 상태 노출을 위한 delay 임의 설정
        goodsListUseCase().executeResult(
            onSuccess = { mainViewState.value = MainViewState.MainList(it) },
            onFailure = { mainViewState.value = MainViewState.Error }
        )
    }

    override fun requestLikeChange(
        id: Int,
        isLiked: Boolean,
        onSuccess: (Boolean) -> Unit,
    ) {
        onSuccess(goodLikeUseCase(id, isLiked))
    }
}



