package com.simplekjl.domain.usecases

import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result
import com.simplekjl.domain.utils.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetImageUrlByLocationUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: ImageFetcherRepository
) : SuspendUseCase<LocationDetails, Result<SearchPhotoPayload>>(dispatcher) {
    override suspend fun execute(parameters: LocationDetails): Result<SearchPhotoPayload> =
        repository.getImagesForLocation(lat = parameters.lat, lon = parameters.lon)
}

data class LocationDetails(val lat: Double, val lon: Double)
