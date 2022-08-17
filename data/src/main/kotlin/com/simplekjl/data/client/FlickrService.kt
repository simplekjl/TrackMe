package com.simplekjl.data.client

import com.simplekjl.domain.model.SearchPhotoPayload
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FlickrService {
    @Headers("Content-Type: application/json")
    @GET("?method=flickr.photos.search&format=json&accuracy=2&extras=url_m&per_page=1&nojsoncallback=1&page=1")
    suspend fun searchPhotoByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Response<SearchPhotoPayload>
}
