package com.wattpad.sandman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mopub.common.MoPub
import com.mopub.mobileads.DefaultInterstitialAdListener
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial

class SandmanFragment : Fragment() {
    private lateinit var parentActivity: FragmentActivity
    private var moPubInterstitial: MoPubInterstitial? = null
    private val adKeywords = "partner:ironsource"
    private val adUnitId by lazy { parentActivity.getString(R.string.interstitial_ad_unit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = (activity as FragmentActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sandman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!MoPub.isSdkInitialized()) {
            return
        }
        showIronSourceInterstitial()
    }

    private fun showIronSourceInterstitial() {
        moPubInterstitial = MoPubInterstitial(parentActivity, adUnitId).apply {
            interstitialAdListener = moPubInterstitialListener
            keywords = adKeywords
            load()
        }
    }

    private val moPubInterstitialListener = object : DefaultInterstitialAdListener() {
        override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
            moPubInterstitial?.show()
        }

        override fun onInterstitialFailed(
            interstitial: MoPubInterstitial?,
            errorCode: MoPubErrorCode?
        ) {
            Toast.makeText(
                parentActivity,
                "Ad load failed: ${errorCode?.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
            if (!parentActivity.supportFragmentManager.isStateSaved) {
                parentActivity.supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moPubInterstitial?.destroy()
    }

    companion object {
        fun newInstance() = SandmanFragment()
    }
}
