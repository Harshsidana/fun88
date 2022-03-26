package com.sprinter.android

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.os.RemoteException
import android.view.KeyEvent
import android.view.WindowManager
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.sprinter.android.helpers.FirebaseAnalyticsHelper
import com.sprinter.android.helpers.FirebaseAnalyticsHelper.trackReferral
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var webViewPop: WebView
    lateinit var contextPop: Context
    lateinit var builder: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent.getStringExtra(Constants.URL_EXTRA)?.let { setupWebView(it) }
    }


    private fun setupWebView(url: String) {
        val webSettings: WebSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webview.webViewClient = WebViewClientImpl(this)
        webview.isHorizontalScrollBarEnabled = false;
        webview.isVerticalScrollBarEnabled = false
        webSettings.domStorageEnabled = true
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
        webSettings.setAppCachePath("/data/data/$packageName/cache");
        webSettings.setAppCacheEnabled(true);
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.userAgentString = "useragent"
        webview.loadUrl(url)

        contextPop = this.applicationContext;
        webview.webChromeClient = CustomChromeClient()
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(true)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webview.canGoBack()) {
                        webview.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    inner class CustomChromeClient : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView, isDialog: Boolean,
            isUserGesture: Boolean, resultMsg: Message
        ): Boolean {
            webViewPop = contextPop.let { WebView(it) }
            webViewPop.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    // to continue loading a given URL in the current WebView.
                    // needed to handle redirects.
                    return false
                }
            }

            // Enable Cookies
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= 21) {
                cookieManager.setAcceptThirdPartyCookies(webViewPop, true)
                cookieManager.setAcceptThirdPartyCookies(webview, true)
            }
            val popSettings: WebSettings = webViewPop.getSettings()
            // WebView tweaks for popups
            webViewPop.isVerticalScrollBarEnabled = false
            webViewPop.isHorizontalScrollBarEnabled = false
            popSettings.javaScriptEnabled = true
            popSettings.saveFormData = true
            popSettings.setEnableSmoothTransition(true)
            // Set User Agent
            popSettings.userAgentString = "useragent"
            // to support content re-layout for redirects
            popSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

            // handle new popups
            webViewPop.webChromeClient = WebChromeClient()

            // set the WebView as the AlertDialog.Builder’s view
            builder = AlertDialog.Builder(this@MainActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .create()
            builder.setTitle("")
            builder.setView(webViewPop)
            builder.show()
            builder.window
                ?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            builder.setOnDismissListener {
                webview.goBack()
            }
            val transport = resultMsg.obj as WebViewTransport
            transport.webView = webViewPop
            resultMsg.sendToTarget()
            return true
        }

        override fun onCloseWindow(window: WebView) {
            try {
                builder.dismiss()
            } catch (e: Exception) {
            }

        }
    }

}