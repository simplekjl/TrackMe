package com.simplekjl.trackme.ui.trackimages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import com.simplekjl.domain.usecases.LocationDetails
import com.simplekjl.domain.utils.Result.Error
import com.simplekjl.domain.utils.Result.Success
import kotlinx.coroutines.runBlocking

class TrackingViewModel(private val getImageUrlByLocationUseCase: GetImageUrlByLocationUseCase) :
    ViewModel() {

    private val _imageList = MutableLiveData(mutableListOf<String>())
    val imageList: LiveData<MutableList<String>> = _imageList

    fun downLoadImage(last: LatLng, distance: Double) {
        if (kotlin.math.floor(distance) > (_imageList.value?.size ?: 0)) {
            runBlocking {
                val result =
                    getImageUrlByLocationUseCase(LocationDetails(last.latitude, last.longitude))
                when (result) {
                    is Error -> {
                        // do not add nothing
                    }
                    is Success -> {
                        _imageList.value?.apply {
                            add(result.data.photoResponse.photo.first().urlImage)
                            _imageList.postValue(this)
                        }
                    }
                }
            }
        }
    }
}