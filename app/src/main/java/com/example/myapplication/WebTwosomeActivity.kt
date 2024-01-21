package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebTwosomeActivity : AppCompatActivity() {
    lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_menu1)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        web = findViewById(R.id.webView1)

        web.webViewClient = MyWebViewClient()

        val webSettings = web.settings
        webSettings.builtInZoomControls = true

        web.loadUrl("https://mo.twosome.co.kr/mn/menuInfoList.do")
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            // Load URL in WebView
            view?.loadUrl(request?.url.toString())
            return true // Indicates that the current WebView will handle the URL request
        }
    }
}
