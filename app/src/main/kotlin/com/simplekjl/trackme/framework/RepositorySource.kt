package com.simplekjl.trackme.framework

import com.simplekjl.data.client.FlickrService
import com.simplekjl.data.repository.NetworkSource
import org.koin.java.KoinJavaComponent.inject

class RepositoriesSource : NetworkSource {
    private val client: FlickrService by inject(FlickrService::class.java)
    override fun getGithubClient(): FlickrService = client
}
