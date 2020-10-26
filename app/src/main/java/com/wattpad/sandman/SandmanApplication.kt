package com.wattpad.sandman

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration

class SandmanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleListener() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                initialise(activity)
                MoPub.onCreate(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                MoPub.onResume(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                MoPub.onPause(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                MoPub.onStart(activity)
            }

            override fun onActivityStopped(activity: Activity) {
                MoPub.onStop(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                MoPub.onDestroy(activity)
            }
        })
    }

    fun initialise(activity: Activity) {
        val sdkConfiguration = SdkConfiguration
            .Builder(activity.getString(R.string.interstitial_ad_unit))
            .build()

        MoPub.initializeSdk(activity, sdkConfiguration) {
            // Let's revoke GDPR consent
            MoPub.getPersonalInformationManager()?.revokeConsent()
        }
    }
}