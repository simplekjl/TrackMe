package com.simplekjl.domain.usecases

import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result

class GetImageUrlByLocationUseCase(
    private val repository: ImageFetcherRepository
) {
    suspend fun getRepositories( lat: Double, lon: Double): Result<SearchPhotoPayload> =
        repository.getMatchingRepositories(lat = lat, lon = lon)
}
