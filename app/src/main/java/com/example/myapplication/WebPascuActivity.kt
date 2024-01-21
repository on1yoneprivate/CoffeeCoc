package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText

class WebPascuActivity: AppCompatActivity() {
    lateinit internal var edtUrl : EditText
    lateinit internal var btnGo : Button
    lateinit internal var btnBack : Button
    lateinit internal var web : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_menu1)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        web = findViewById<WebView>(R.id.webView1)

        web.webViewClient = webViewClient()

        var webSet = web.settings
        webSet.builtInZoomControls = true

        web.loadUrl("https://www.caffe-pascucci.co.kr/product/productList.asp")
    }

    class webViewClient:WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }
}

