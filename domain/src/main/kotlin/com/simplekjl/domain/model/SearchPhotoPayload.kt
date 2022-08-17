package com.simplekjl.domain.model

import com.google.gson.annotations.SerializedName

/**
 * {
"photos": {
"page": 1,
"pages": 277547,
"perpage": 1,
"total": 277547,
"photo": [
{
"id": "52288619352",
"owner": "15622257@N06",
"secret": "cfb8bab471",
"server": "65535",
"farm": 66,
"title": "Drohne 19",
"ispublic": 1,
"isfriend": 0,
"isfamily": 0,
"url_m": "https://live.staticflickr.com/65535/52288619352_cfb8bab471.jpg",
"height_m": 375,
"width_m": 500
}
]
},
"stat": "ok"
}
 */
data class SearchPhotoPayload(
    @SerializedName("stat")
    var status: String,
    @SerializedName("photos")
    var photoResponse: PhotoResponse
)

data class PhotoResponse(
    @SerializedName("photo")
    var photo: ArrayList<Photo> = arrayListOf()
)

data class Photo(
    @SerializedName("url_m") var urlImage: String
)