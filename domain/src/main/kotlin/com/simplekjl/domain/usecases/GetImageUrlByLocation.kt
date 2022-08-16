package com.simplekjl.domain.usecases

import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result

class GetImageUrlByLocation(
    private val repository: ImageFetcherRepository
) {
    suspend fun getRepositories( lat: Int, lon: Int): Result<SearchPhotoPayload> =
        repository.getMatchingRepositories(lat = lat, lon = lon)
}
