package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton

import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker

import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import java.util.Locale

class MainActivity : AppCompatActivity() {


    lateinit var btnCoffee : Button
    lateinit var btnDecaf: Button
    lateinit var btnNon: Button
    lateinit var btnFrappe: Button
    lateinit var btnTea: Button
    lateinit var btnAde: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // NaverMap SDK 클라이언트 ID 등록
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("navejkvdnc")

        // main_activity.xml을 화면에 표시
        setContentView(R.layout.activity_main)

        // 최하단 바의 map 버튼 (nav_map ID를 가진 버튼) 찾기
        val navMapButton: ImageButton = findViewById(R.id.nav_map)
        // 버튼 클릭 이벤트 처리
        navMapButton.setOnClickListener {
            // 버튼이 클릭되면 MapActivity로 화면 전환
            val intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        }

        // 최상단의 검색창 버튼
        val searchButton: Button = findViewById(R.id.search)

        searchButton.setOnClickListener {
            // 버튼이 클릭되면 SearchActivity로 화면 전환
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }

        // 메뉴 카테고리 버튼 등록
        btnCoffee = findViewById(R.id.btn_coffee)
        btnDecaf = findViewById(R.id.btn_decaf)
        btnNon = findViewById(R.id.btn_non)
        btnFrappe = findViewById(R.id.btn_frappe)
        btnTea = findViewById(R.id.btn_tea)
        btnAde = findViewById(R.id.btn_ade)

        // 커피 카테고리 버튼 (클릭 시 커피 창으로 전환)
        btnCoffee.setOnClickListener {
            val intent = Intent(this, CoffeeActivity::class.java)
            startActivity(intent)
        }
        // 디카페인 카테고리 버튼 (클릭 시 디카페인 창으로 전환)
        btnDecaf.setOnClickListener {
            val intent = Intent(this, DecafActivity::class.java)
            startActivity(intent)
        }
        // 논커피 카테고리 버튼 (클릭 시 논커피 창으로 전환)
        btnNon.setOnClickListener {
            val intent = Intent(this, NoncoffeeActivity::class.java)
            startActivity(intent)
        }
        // 프라페 카테고리 버튼 (클릭 시 프라페 창으로 전환)
        btnFrappe.setOnClickListener {
            val intent = Intent(this, FrappeActivity::class.java)
            startActivity(intent)
        }
        // 티 카테고리 버튼 (클릭 시 티 창으로 전환)
        btnTea.setOnClickListener {
            val intent = Intent(this, TeaActivity::class.java)
            startActivity(intent)
        }
        // 에이드 카테고리 버튼 (클릭 시 에이드 창으로 전환)
        btnAde.setOnClickListener {
            val intent = Intent(this, AdeActivity::class.java)
            startActivity(intent)
        }

    }






    private fun toast(text: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }



}