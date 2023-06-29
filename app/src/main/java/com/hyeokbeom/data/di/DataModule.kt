package com.hyeokbeom.data.di

import com.hyeokbeom.data.repository.RXCRepositoryImpl
import com.hyeokbeom.domain.repository.RXCRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun rxcRepositoryBinder(
        repository: RXCRepositoryImpl,
    ): RXCRepository
}