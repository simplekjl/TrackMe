package com.simplekjl.domain.di

import com.simplekjl.domain.usecases.GetImageUrlByLocation
import org.koin.dsl.module

val domainModule = module {
    factory { GetImageUrlByLocation(get()) }
}