package com.fun88.android.helpers

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsHelper {

    fun getInstance(): FirebaseAnalytics {
        return Firebase.analytics
    }

    fun FirebaseAnalytics.trackReferral(
        referrerUrl: String?,
        referrerClickTime: Long?,
        appInstallTime: Long?,
        instantExperienceLaunched: Boolean?,
        refrer: String?
    ) {
        this.logEvent(
            FirebaseAnalytics.Event.SELECT_ITEM,
            bundleOf(
                Pair("referrerUrl", referrerUrl),
                Pair("referrerClickTime", referrerClickTime),
                Pair("appInstallTime", appInstallTime),
                Pair("instantExperienceLaunched", instantExperienceLaunched),
                Pair("refrer", refrer)
            )
        )
    }

}
