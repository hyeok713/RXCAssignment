package com.hyeokbeom.domain.repository

import com.hyeokbeom.domain.model.GoodsListResponse

interface RXCRepository {
    suspend fun mainList(): GoodsListResponse
}