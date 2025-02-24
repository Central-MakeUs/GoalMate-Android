package cmc.goalmate.presentation.ui.common

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

enum class WebScreenUrl(val url: String) {
    FAQ("https://ash-oregano-9dc.notion.site/5b001277dea44b779bd41dd11550e13c?pvs=4"),
    PrivacyPolicy("https://www.notion.so/997827990f694f63a60b06c06beb1468?pvs=4"),
    TermsOfService("https://ash-oregano-9dc.notion.site/f97185c23c5444b4ae3796928ae7f646?pvs=4"),
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun GoalMateWebScreen(targetUrl: WebScreenUrl) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                webViewClient = WebViewClient()

                loadUrl(targetUrl.url)
            }
        },
    )
}
