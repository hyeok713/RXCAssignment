package com.hyeokbeom.rxcassignment

import androidx.lifecycle.ViewModel
import com.hyeokbeom.domain.usecase.MainListUseCase
import com.hyeokbeom.rxcassignment.ext.launchOnIO
import com.hyeokbeom.shared.Log
import com.hyeokbeom.shared.executeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainListUseCase: MainListUseCase
): ViewModel(), MainViewIntent {
    fun haha() {
        println("하하!")
    }
    val mainViewState = MutableStateFlow<Int>(0)
    init {
        launchOnIO {
            mainListUseCase().executeResult(
                onSuccess = {
                    Log.e(it.toString())
                },
                onFailure = {
                    Log.e("실패했음")
                }
            )
        }
    }

    override fun a() {
    }

    override fun b() {
    }
}

/**
 * MainViewIntent
 * [메인 뷰 Intent 관리]
 */
interface MainViewIntent {
    fun a()
    fun b()
}

/**
 * MainViewState
 * [메인 뷰 State 관리]
 */
sealed class MainViewState {
    object IsLoading: MainViewState()   // 데이터 로딩 상태
    object Error: MainViewState()       // 데이터 로딩 실패
    data class MainList(val list: List<Int>): MainViewState()   // 데이터 로딩 성공시 with list
}
