package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText

class WebStabucksActivity: AppCompatActivity() {
    lateinit internal var edtUrl : EditText
    lateinit internal var btnGo : Button
    lateinit internal var btnBack : Button
    lateinit internal var web : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_menu1)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        // 지도 창 xml
        web = findViewById<WebView>(R.id.webView1)

        web.webViewClient = webViewClient()

        var webSet = web.settings
        webSet.builtInZoomControls = true

        // 스타벅스 웹페이지 메뉴 링크
        web.loadUrl("https://www.starbucks.co.kr/menu/drink_list.do")
    }

    class webViewClient:WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }
}

