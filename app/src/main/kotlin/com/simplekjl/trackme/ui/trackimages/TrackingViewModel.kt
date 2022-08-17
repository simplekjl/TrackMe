package com.simplekjl.trackme.ui.trackimages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import com.simplekjl.trackme.extension.mutableLiveDataOf

class TrackingViewModel(private val getImageUrlByLocationUseCase: GetImageUrlByLocationUseCase) :
    ViewModel() {

    private val _isTracking = mutableLiveDataOf(false)
    val isTracking: LiveData<Boolean> = _isTracking



}