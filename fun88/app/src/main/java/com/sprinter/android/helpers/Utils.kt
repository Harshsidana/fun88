package com.sprinter.android.helpers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri


object Utils {
    fun tryChromeOrAnyBrowserIfAvailable(url: String?, context: Context) {
        val googleChromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        googleChromeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        googleChromeIntent.setPackage("com.android.chrome")
        try {
            context.startActivity(googleChromeIntent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            val availFrequentlyUsedBrowser: String
            val packageManager: PackageManager = context.getPackageManager()
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(url)
            val list = packageManager.queryIntentActivities(
                browserIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            if (list != null && !list.isEmpty()) {
                availFrequentlyUsedBrowser = list[0].activityInfo.packageName
                val availBrowswerIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                availBrowswerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                availBrowswerIntent.setPackage(availFrequentlyUsedBrowser)
                try {
                    context.startActivity(availBrowswerIntent)
                } catch (e: ActivityNotFoundException) {
                }
            }
        }
    }
    fun prependHttpProtocolIfRequired(url: String): String? {
        var resultUrl = url
        if (!resultUrl.startsWith("http://") && !resultUrl.startsWith("https://")) {
            resultUrl = "https://$resultUrl"
        }
        return resultUrl
    }
}