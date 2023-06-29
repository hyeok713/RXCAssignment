package com.hyeokbeom.domain.usecase

import javax.inject.Inject

/**
 * GoodLikeUseCase [SAMPLE API]
 * 좋아요 기능 샘플 UseCase
 */
class GoodLikeUseCase @Inject constructor() {
    /**
     * invoke
     * @param id List item's id (index)
     * @param isLiked 좋아요 여부
     */
    operator fun invoke(id: Int, isLiked: Boolean): Boolean {
        return !isLiked
    }
}