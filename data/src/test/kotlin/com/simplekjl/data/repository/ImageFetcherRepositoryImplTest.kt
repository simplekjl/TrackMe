package com.simplekjl.data.repository

import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import com.simplekjl.data.client.FlickrService
import com.simplekjl.data.util.MainCoroutineRule
import com.simplekjl.data.util.runBlockingTest
import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class ImageFetcherRepositoryImplTest {

    private val fixture = kotlinFixture()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var networkMockk: NetworkSource

    @RelaxedMockK
    private lateinit var service: FlickrService

    private lateinit var imageFetcherRepository: ImageFetcherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        imageFetcherRepository = ImageFetcherRepositoryImpl(networkMockk)
    }

    @Test
    fun `check imageFetcher returns success`() =
        mainCoroutineRule.runBlockingTest {
            val lat = 34.0
            val lon = 34.0
            val photoData: SearchPhotoPayload = fixture()
            coEvery {
                imageFetcherRepository.getImagesForLocation(
                    lat,
                    lon
                )
            } returns Result.Success(photoData)
            every { networkMockk.getGithubClient() } returns service
            coEvery {
                networkMockk.getGithubClient().searchPhotoByLocation(lat, lon)
            } returns Response.success(photoData)

            val result = imageFetcherRepository.getImagesForLocation(lat, lon)
            assertThat(result).isInstanceOf(Result.Success::class.java)

        }

    @After
    fun tearDown() {
        unmockkAll()
    }
}