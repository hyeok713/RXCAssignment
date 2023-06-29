package com.hyeokbeom.data.datasource

import com.hyeokbeom.domain.model.GoodsListResponse
import retrofit2.http.GET

interface RXCApi {
    @GET("goodslist.json")
    suspend fun mainList(): GoodsListResponse
}