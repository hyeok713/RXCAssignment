package com.hyeokbeom.rxcassignment

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.domain.usecase.GoodsListUseCase
import com.hyeokbeom.rxcassignment.ext.launchOnIO
import com.hyeokbeom.shared.executeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.reflect.KFunction0

@HiltViewModel
class MainViewModel @Inject constructor(
    private val goodsListUseCase: GoodsListUseCase
): ViewModel(), MainViewIntent {
    val mainViewState = MutableStateFlow<MainViewState>(MainViewState.Idle)

    override fun fetchGoodsList() = launchOnIO {
        goodsListUseCase().executeResult(
            onSuccess = {
                mainViewState.value = MainViewState.MainList(it)
            },
            onFailure = {
                mainViewState.value = MainViewState.Error
            }
        )
    }

    override fun b() {
    }
}

/**
 * MainViewIntent
 * [메인 뷰 Intent 관리]
 */
interface MainViewIntent {
    fun fetchGoodsList(): Job
    fun b()
}

/**
 * MainViewState
 * [메인 뷰 State 관리]
 */
sealed class MainViewState {
    object Idle: MainViewState()    // 유휴 상태
    object IsLoading: MainViewState()   // 데이터 로딩 상태
    object Error: MainViewState()       // 데이터 로딩 실패
    data class MainList(val list: List<GoodsListResponse.Good>): MainViewState()   // 데이터 로딩 성공시 with list
}
