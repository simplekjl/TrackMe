package com.simplekjl.data.repository

import com.simplekjl.data.client.FlickrService


interface NetworkSource {
    fun getGithubClient(): FlickrService
}
