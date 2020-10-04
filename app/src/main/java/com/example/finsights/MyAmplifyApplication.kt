package com.example.finsights

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin


class MyAmplifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.addPlugin(AWSPredictionsPlugin())
        try {
            Amplify.configure(applicationContext)
            Log.i("--------------------------------------------------MyAmplifyApp----------------------------", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("====================================MyAmplifyApp===========================", "Could not initialize Amplify", error)
        }
    }
}