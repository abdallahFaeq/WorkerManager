package com.training.workmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import java.util.UUID
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG: String? = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnOneTime)
            .setOnClickListener{
                startOneTimeWork()
            }

        findViewById<Button>(R.id.btnPeriodic)
            .setOnClickListener{
                startPeriodicTimeWork()
            }
    }

    fun startOneTimeWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(true)
            .build()

        val myWorkRequest:WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()

        // submit request
        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startPeriodicTimeWork(){
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myWorkRequest  = PeriodicWorkRequest.Builder(MyWorker::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("my_id")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("my_id",ExistingPeriodicWorkPolicy.KEEP,myWorkRequest)
    }



}

/*  WorkManager library
    it is used for long running and persisten background tasks and jobs

    by persistent we mean it remains scheduled even if your app restarts or your phone reboots
    it is a part of jetpack archeticture components
    it can be of two types: one time or periodic

 */