package com.hyeokbeom.rxcassignment.main

import kotlinx.coroutines.Job

/**
 * MainViewIntent
 * [메인 뷰 Intent]
 */
interface MainViewIntent {
    /*
     * fetchGoodList
     * [상품 리스트 조회]
     */
    fun fetchGoodsList(): Job

    /*
     * requestLikeChange
     * [좋아요 클릭 상태 변경 요청]
     */
    fun requestLikeChange(index: Int): Job
}