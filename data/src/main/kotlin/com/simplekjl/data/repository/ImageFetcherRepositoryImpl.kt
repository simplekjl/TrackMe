package com.simplekjl.data.repository

import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result

class ImageFetcherRepositoryImpl(
    private val network: NetworkSource
) : ImageFetcherRepository {

    override suspend fun getImagesForLocation(
        lat: Double,
        lon: Double
    ): Result<SearchPhotoPayload> {
        return try {
            val call = network.getGithubClient()
                .searchPhotoByLocation(lat = lat, lon = lon)
            if (call.isSuccessful) {
                Result.Success(call.body()!!)
            } else {
                Result.Error(Exception(call.message()))
            }
        } catch (ex: Exception) {
            Result.Error(Exception(ex.message))
        }
    }

}

