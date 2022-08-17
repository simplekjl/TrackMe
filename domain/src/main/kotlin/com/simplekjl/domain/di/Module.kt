package com.simplekjl.domain.di

import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetImageUrlByLocationUseCase(get()) }
}