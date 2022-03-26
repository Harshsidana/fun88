package com.fun88.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.fun88.android.Constants.URL_EXTRA
import com.fun88.android.helpers.ConfigHandler
import com.fun88.android.helpers.Utils
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class SplashActivity : AppCompatActivity() {

    private var mFirebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getDataRemoteConfig()
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