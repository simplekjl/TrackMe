package com.simplekjl.domain.mapper

import com.simplekjl.domain.model.PhotoItem
import com.simplekjl.domain.model.PhotoRaw
import java.util.*


fun ArrayList<PhotoRaw>.toListModel(): List<PhotoItem> {
    val newList = mutableListOf<PhotoItem>()
    this.forEach {
        newList.add(PhotoItem(UUID.randomUUID().toString(), it.urlImage))
    }
    return newList
}