package com.hyeokbeom.data.repository

import com.hyeokbeom.data.datasource.RXCApi
import com.hyeokbeom.domain.repository.RXCRepository
import javax.inject.Inject

class RXCRepositoryImpl @Inject constructor(
    private val api: RXCApi,
) : RXCRepository {
    override suspend fun goodsList() = api.mainList()
}