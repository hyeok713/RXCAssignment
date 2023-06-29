package com.hyeokbeom.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

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
    ) {
        companion object {
            @TestOnly
            val SAMPLE: Good = Good(
                brandName = "밀로",
                name = "Random Dice Campaign Hoodie Black",
                consumerPrice = 78000,
                discountRate = 20,
                price = 62400,
                thumbnailUrl = "https://cdn-image.prizm.co.kr/goods/20220325/fbccfdf5-135c-4484-b1cf-544a9b18465a.jpg"
            )
        }
    }

}