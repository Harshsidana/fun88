package com.sprinter.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.view.WindowManager
import android.widget.Toast
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.sprinter.android.Constants.URL_EXTRA
import com.sprinter.android.helpers.ConfigHandler
import com.sprinter.android.helpers.Utils
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.sprinter.android.helpers.FirebaseAnalyticsHelper
import com.sprinter.android.helpers.FirebaseAnalyticsHelper.trackReferral

class SplashActivity : AppCompatActivity() {
    var referrerClient: InstallReferrerClient? = null
    private var mFirebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getDataRemoteConfig()
        setupInstallReferrer()

    }
    private fun setupInstallReferrer() {
        referrerClient = InstallReferrerClient.newBuilder(this).build();
        referrerClient?.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        var response: ReferrerDetails? = null
                        try {
                            response = referrerClient?.getInstallReferrer()
                            val referrerUrl = response?.installReferrer
                            val referrerClickTime = response?.referrerClickTimestampSeconds
                            val appInstallTime = response?.installBeginTimestampSeconds
                            val instantExperienceLaunched = response?.googlePlayInstantParam
                            val refrer = response?.installReferrer
                            FirebaseAnalyticsHelper.getInstance().trackReferral(
                                referrerUrl,
                                referrerClickTime,
                                appInstallTime,
                                instantExperienceLaunched,
                                refrer
                            )
                        } catch (e: RemoteException) {
                            e.printStackTrace()
                        }
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED ->                         // API not available on the current Play Store app.
                        Toast.makeText(
                            this@SplashActivity,
                            "Feature not supported..",
                            Toast.LENGTH_SHORT
                        ).show()
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE ->                         // Connection couldn't be established.
                        Toast.makeText(
                            this@SplashActivity,
                            "Fail to establish connection",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Toast.makeText(this@SplashActivity, "Service disconnected..", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
    private fun getDataRemoteConfig() {
        mFirebaseRemoteConfig.setConfigSettingsAsync(FirebaseRemoteConfigSettings.Builder().build())
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val appConfig= ConfigHandler.getAppConfig()
                    when(appConfig?.type)
                    {
                        Constants.TYPE_CHROME_REDIRECT->{
                            Utils.tryChromeOrAnyBrowserIfAvailable(Utils.prependHttpProtocolIfRequired(appConfig.url),this)
                            finish()
                        }
                        Constants.TYPE_INTERNAL_WEBVIEW->{
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(URL_EXTRA,appConfig.url)
                            startActivity(intent)
                            finish()
                        }
                        Constants.TYPE_LANDING_PAGE->{

                        }
                    }

                } else {
                    Toast.makeText(this,"Something went wrong, please try again",Toast.LENGTH_LONG).show();
                }
            }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}