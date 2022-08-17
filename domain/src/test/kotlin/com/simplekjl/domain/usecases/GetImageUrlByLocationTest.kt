package com.simplekjl.domain.usecases

import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.util.MainCoroutineRule
import com.simplekjl.util.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetImageUrlByLocationTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var imageFetcherRepository: ImageFetcherRepository

    private lateinit var userCase: GetImageUrlByLocation

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        userCase = GetImageUrlByLocation(
            repository = imageFetcherRepository
        )
    }

    @Test
    fun gettingImage() = mainCoroutineRule.runBlockingTest {
        val lat = 35.0
        val long = 40.0

        userCase.getRepositories(lat, long)

        coVerify {
            imageFetcherRepository.getMatchingRepositories(lat, long)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}