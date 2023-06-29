package com.hyeokbeom.domain.usecase

import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.domain.repository.RXCRepository
import com.hyeokbeom.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainListUseCase @Inject constructor(
    private val repository: RXCRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : NonParamUseCaseExecutor<GoodsListResponse>(dispatcher) {
    override suspend fun execute(): GoodsListResponse = repository.mainList()
}