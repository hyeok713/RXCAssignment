package com.hyeokbeom.rxcassignment.main

import kotlinx.coroutines.Job

/**
 * MainViewIntent
 * [메인 뷰 Intent]
 */
interface MainViewIntent {
    /**
     * fetchGoodList
     * [상품 리스트 조회]
     */
    fun fetchGoodsList(): Job

    /**
     * requestLikeChange
     * [좋아요 클릭 상태 변경 요청]
     * @param id List item's id
     * @param isLiked 좋아요 여부
     * @param onSuccess 요청 처리 결과
     */
    fun requestLikeChange(
        id: Int,
        isLiked: Boolean,
        onSuccess: (Boolean) -> Unit
    )
}