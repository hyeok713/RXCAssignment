package com.hyeokbeom.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

class GoodsListResponse : ArrayList<GoodsListResponse.Good>(){
    @Serializable
    data class Good(
        @SerializedName("brandName")
        val brandName: String,
        @SerializedName("consumerPrice")
        val consumerPrice: Int,
        @SerializedName("discountRate")
        val discountRate: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("thumbnailUrl")
        val thumbnailUrl: String
    )
}