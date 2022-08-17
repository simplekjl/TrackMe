package com.simplekjl.domain.imagefetcher

import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result


interface ImageFetcherRepository {
    suspend fun getImagesForLocation(
        lat: Double,
        lon: Double
    ): Result<SearchPhotoPayload>
}
