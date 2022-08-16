package com.simplekjl.domain.imagefetcher

import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result


interface ImageFetcherRepository {
    suspend fun getMatchingRepositories(
        lat: Int,
        lon: Int
    ): Result<SearchPhotoPayload>
}
