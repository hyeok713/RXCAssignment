package com.hyeokbeom.rxcassignment.main

import com.hyeokbeom.domain.model.GoodsListResponse

/**
 * MainViewState
 * [메인 뷰 State]
 */
sealed class MainViewState {
    object Idle : MainViewState()        // 유휴 상태
    object IsLoading : MainViewState()   // 데이터 로딩 상태
    object Error : MainViewState()       // 데이터 로딩 실패
    data class MainList(val list: List<GoodsListResponse.Good>) :
        MainViewState()   // 데이터 로딩 성공시 with list
}
