package com.simplekjl.domain.di

import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetImageUrlByLocationUseCase(Dispatchers.IO, get()) }
}