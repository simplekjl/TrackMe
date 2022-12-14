package com.simplekjl.trackme.ui.home.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.simplekjl.domain.model.PhotoRaw
import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import com.simplekjl.domain.usecases.LocationDetails
import com.simplekjl.domain.utils.Result.Error
import com.simplekjl.domain.utils.Result.Success
import kotlinx.coroutines.launch
import kotlin.random.Random

class TrackingViewModel(private val getImageUrlByLocationUseCase: GetImageUrlByLocationUseCase) :
    ViewModel() {

    private val _imageList = MutableLiveData(mutableListOf<String>())
    val imageList: LiveData<MutableList<String>> = _imageList

    fun shouldDownLoadImage(locationsList: MutableList<LatLng>, distance: Double) {
        if (locationsList.isEmpty()) return // return if it is empty
        if (kotlin.math.floor(distance) > (_imageList.value?.size ?: 0)) {
            viewModelScope.launch {
                val result =
                    getImageUrlByLocationUseCase(
                        LocationDetails(
                            locationsList.last().latitude,
                            locationsList.last().longitude
                        )
                    )
                when (result) {
                    is Error -> {
                        addErrorImageToList()
                    }
                    is Success -> {
                        addImageToList(result.data.photoResponse.photo)
                    }
                }
            }
        }
    }

    private fun addImageToList(data: ArrayList<PhotoRaw>) {
        _imageList.value?.apply {
            if (data.isNotEmpty()) {
                add(data[Random.nextInt(data.size)].urlImage)
            } else {
                addErrorImageToList()
            }
            _imageList.postValue(this)
        }
    }

    private fun addErrorImageToList() {
        _imageList.value?.apply {
            add("https://i.pinimg.com/564x/83/95/ec/8395ec481687ee79f74a345c2ba184ac.jpg")
            _imageList.postValue(this)
        }
    }

    fun clearImages() {
        _imageList.value = mutableListOf()
    }
}