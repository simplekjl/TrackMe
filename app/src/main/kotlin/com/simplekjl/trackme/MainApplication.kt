package com.simplekjl.trackme

import android.app.Application
import com.facebook.stetho.Stetho
import com.simplekjl.data.client.FlickrService
import com.simplekjl.data.di.dataModule
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {

    private val networkModule = createNetworkModule()
    private val mainModule = createMainModule() // UI level


    private fun createMainModule() = module {
        single<Retrofit> {
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
                val requestBuilder: Request.Builder = original.newBuilder()
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
        single<FlickrService> { get<Retrofit>().create(FlickrService::class.java) }
    }

    private fun createNetworkModule() = module {

    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        //start koin
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    dataModule,
                    networkModule,
                    mainModule
                )
            )
        }
    }
}