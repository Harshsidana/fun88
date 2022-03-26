package com.fun88.android

import android.app.Activity
import android.webkit.WebView
import android.webkit.WebViewClient


class WebViewClientImpl : WebViewClient {
    var activity:Activity?=null

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun shouldOverrideUrlLoading(webView: WebView?, url: String): Boolean {
        webView?.loadUrl(url);
        return false
    }

}