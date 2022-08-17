package com.simplekjl.trackme.extension

import androidx.lifecycle.MutableLiveData

fun <T : Any> mutableLiveDataOf() = MutableLiveData<T>()
fun <T : Any> mutableLiveDataOf(value: T) = MutableLiveData(value)