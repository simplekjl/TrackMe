package com.simplekjl.data.di

import com.simplekjl.data.repository.ImageFetcherRepositoryImpl
import com.simplekjl.data.repository.NetworkSource
import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import org.koin.dsl.module

val dataModule = createDataModule()


private fun createDataModule() = module {
    factory<ImageFetcherRepository> { ImageFetcherRepositoryImpl(get()) }
}