package com.simplekjl.domain.usecases

import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import com.simplekjl.domain.imagefetcher.ImageFetcherRepository
import com.simplekjl.domain.model.SearchPhotoPayload
import com.simplekjl.domain.utils.Result
import com.simplekjl.util.MainCoroutineRule
import com.simplekjl.util.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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

    @MockK
    private lateinit var imageFetcherRepository: ImageFetcherRepository

    private lateinit var userCase: GetImageUrlByLocationUseCase
    private val fixture = kotlinFixture()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        userCase = GetImageUrlByLocationUseCase(
            mainCoroutineRule.testDispatcher,
            repository = imageFetcherRepository
        )
    }

    @Test
    fun gettingImageURl() {
        val locationDetails = LocationDetails(35.0, 40.0)
        val response: SearchPhotoPayload = fixture()
        coEvery { userCase(locationDetails) } returns Result.Success(response)
        mainCoroutineRule.runBlockingTest {
            val result = userCase(locationDetails)
            assertThat((result as Result.Success).data).isInstanceOf(SearchPhotoPayload::class.java)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}