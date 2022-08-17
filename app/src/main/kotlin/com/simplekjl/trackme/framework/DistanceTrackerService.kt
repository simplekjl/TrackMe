package com.simplekjl.trackme.framework

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent

class DistanceTrackerService(
    private val dispatchers: Dispatchers,
    private val get: GetImageUrlByLocationUseCase,
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters),
    KoinComponent {
    override suspend fun doWork(): Result = Result.success()

}