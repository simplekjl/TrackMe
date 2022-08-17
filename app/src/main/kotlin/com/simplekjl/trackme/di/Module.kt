package com.simplekjl.trackme.di

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.simplekjl.data.client.FlickrService
import com.simplekjl.data.repository.NetworkSource
import com.simplekjl.trackme.BuildConfig
import com.simplekjl.trackme.R
import com.simplekjl.trackme.framework.RepositoriesSource
import com.simplekjl.trackme.ui.trackimages.TrackingViewModel
import com.simplekjl.trackme.utils.Constants.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = createMainModule() // UI level


@SuppressLint("UnspecifiedImmutableFlag")
private fun createMainModule() = module {
    single {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(0, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url
            val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.FLICKR_API_KEY)
                .build()

            // Request customization: add request headers
            val requestBuilder: Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })

        val client = builder.build()

        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_FLICKR_URL_API)
            .build()
    }
    single { get<Retrofit>().create(FlickrService::class.java) }
    factory<NetworkSource> { RepositoriesSource() }
    factory { params ->
        NotificationCompat.Builder(params.get(), NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_mountain_icon)
            .setContentIntent(params.get())
    }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factory<NotificationManager> { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    viewModel { TrackingViewModel(get()) }
}
