package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker

import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.OverlayImage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

// OnMapReadyCallback을 상속 받는다.
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    val permission_request = 99
    private val LOCATION_PERMISSION_REQUEST_CODE = 5000
    private val marker = Marker()
    // 카페 별 마커
    private var starbucksMarkers = mutableListOf<Marker>()
    private var ediyaMarkers = mutableListOf<Marker>()
    private var composeMarkers = mutableListOf<Marker>()
    private var gateMarkers = mutableListOf<Marker>()
    private var hollysMarkers = mutableListOf<Marker>()
    private var megaMarkers = mutableListOf<Marker>()
    private var mmthMarkers = mutableListOf<Marker>()
    private var pascucciMarkers = mutableListOf<Marker>()
    private var twosomeMarkers = mutableListOf<Marker>()


    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Naver Map 클라이언트 ID
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("navejkvdnc")
        setContentView(R.layout.activity_map_1)

        // onCreate에서 권한을 확인하며 위치 권한이 없을 경우 사용자에게 권한을 요청한다.
        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }//권한 확인

        // 버튼 리스너 설정
        var btnMenu : ImageButton = findViewById(R.id.nav_menu)
        btnMenu.setOnClickListener {
            // 메뉴 버튼 클릭 시 MainActivity로 화면 전환
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 스타벅스
        // 버튼 짧게 클릭 시, 스타벅스의 위치가 마커로 표시된다
        val btnStarbucks = findViewById<Button>(R.id.btn_starbucks)
        btnStarbucks.setOnClickListener {
            // 스타벅스 마커가 지도에 없다면 추가
            if (starbucksMarkers.isEmpty()) {
                addStarbucksMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeStarbucksMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 스타벅스 공식 웹사이트의 메뉴 화면을 띄운다.
        btnStarbucks.setOnLongClickListener {
            val intent = Intent(applicationContext, WebStabucksActivity::class.java)
            startActivity(intent)
            true// 길게 클릭 이벤트가 처리되었음을 나타냅니다.
        }

        // 이디야
        // 버튼 짧게 클릭 시, 이디야의 위치가 마커로 표시된다
        val btnEdiya = findViewById<Button>(R.id.btn_ediya)
        btnEdiya.setOnClickListener {
            // 이디야 마커가 지도에 없다면 추가
            if (ediyaMarkers.isEmpty()) {
                addEdiyaMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeEdiyaMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 이디야 공식 웹사이트의 메뉴 화면을 띄운다
        btnEdiya.setOnLongClickListener{
            // WebEdiyaActivity를 시작합니다.
            val intent = Intent(applicationContext, WebEdiyaActivity::class.java)
            startActivity(intent)
            true  // 길게 클릭 이벤트가 처리되었음을 나타냅니다.
        }

        // 컴포즈
        // 버튼 짧게 클릭 시, 컴포즈의 위치가 마커로 표시된다
        val btnCompose: Button = findViewById(R.id.btn_compose)
        btnCompose.setOnClickListener {
            // 컴포즈 마커가 지도에 없다면 추가
            if (composeMarkers.isEmpty()) {
                addComposeMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeComposeMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 컴포즈 공식 웹사이트의 메뉴 화면을 띄운다
        btnCompose.setOnLongClickListener {
            val intent = Intent(applicationContext, WebComposeActivity::class.java)
            startActivity(intent)
            true
        }

        // 메가 커피
        // 버튼 짧게 클릭 시, 메가커피의 위치가 마커로 표시된다
        val btnMega: Button = findViewById(R.id.btn_mega)
        btnMega.setOnClickListener {
            // 메가커피 마커가 지도에 없다면 추가
            if (megaMarkers.isEmpty()) {
                addMegaMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeMegaMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 메가커피 공식 웹사이트의 메뉴 화면을 띄운다
        btnMega.setOnLongClickListener {
            val intent = Intent(applicationContext, WebMegaActivity::class.java)
            startActivity(intent)
            true
        }

        // 매머드 커피
        // 버튼 짧게 클릭 시, 매머드 커피의 위치가 마커로 표시된다
        val btnMmth: Button = findViewById(R.id.btn_mmth)
        btnMmth.setOnClickListener {
            // 매머드 커피 마커가 지도에 없다면 추가
            if (mmthMarkers.isEmpty()) {
                addMmthMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeMmthMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 매머드 커피 공식 웹사이트의 메뉴 화면을 띄운다
        btnMmth.setOnLongClickListener {
            // 버튼이 클릭되면 activity_map_1로 화면 전환
            val intent = Intent(applicationContext, WebMamothActivity::class.java)
            startActivity(intent)
            true
        }

        // 카페 게이트
        // 버튼 짧게 클릭 시, 카페 기이트의 위치가 마커로 표시된다
        val btnGate: Button = findViewById(R.id.btn_gate)
        btnGate.setOnClickListener {
            // 카페 게이트 마커가 지도에 없다면 추가
            if (gateMarkers.isEmpty()) {
                addGateMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeGateMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 카페 게이트 공식 웹사이트의 메뉴 화면을 띄운다
        btnGate.setOnLongClickListener {
            val intent = Intent(applicationContext, WebGateActivity::class.java)
            startActivity(intent)
            true
        }

        // 할리스
        // 버튼 짧게 클릭 시, 할리스의 위치가 마커로 표시된다
        val btnHollys: Button = findViewById(R.id.btn_hollys)
        btnHollys.setOnClickListener {
            // 할리스 마커가 지도에 없다면 추가
            if (hollysMarkers.isEmpty()) {
                addHollysMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeHollysMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 할리스 공식 웹사이트의 메뉴 화면을 띄운다
        btnHollys.setOnLongClickListener {
            val intent = Intent(applicationContext, WebHollysActivity::class.java)
            startActivity(intent)
            true
        }

        // 파스쿠찌
        // 버튼 짧게 클릭 시, 파스쿠찌의 위치가 마커로 표시된다
        val btnPascucci: Button = findViewById(R.id.btn_pascucci)
        btnPascucci.setOnClickListener {
            // 파스쿠찌 마커가 지도에 없다면 추가
            if (pascucciMarkers.isEmpty()) {
                addPascucciMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removePascucciMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 파스쿠찌 공식 웹사이트의 메뉴 화면을 띄운다
        btnPascucci.setOnLongClickListener {
            val intent = Intent(applicationContext, WebPascuActivity::class.java)
            startActivity(intent)
            true
        }

        // 투썸 플레이스
        // 버튼 짧게 클릭 시, 투썸 플레이스의 위치가 마커로 표시된다
        val btnTwosome: Button = findViewById(R.id.btn_twosome)
        btnTwosome.setOnClickListener {
            // 투썸 플레이스 마커가 지도에 없다면 추가
            if (twosomeMarkers.isEmpty()) {
                addTwosomeMarkers()
            } else {
                // 이미 표시되어있다면 제거
                removeTwosomeMarkers()
            }
        }
        // 버튼을 길게 클릭 시, 투썸 플레이스 공식 웹사이트의 메뉴 화면을 띄운다
        btnTwosome.setOnLongClickListener {
            val intent = Intent(applicationContext, WebTwosomeActivity::class.java)
            startActivity(intent)
            true
        }
    }

    // 권한이 있는지 허락을 받아야한다.
    fun isPermitted(): Boolean {
        // 권한이 있다면 true, 권한이 없다면 false이다
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // 권한이 있다면 startProcess함수를 실행한다.
    fun startProcess(){
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            } //권한
        mapFragment.getMapAsync(this)
    } //권한이 있는것을 확인하고, 권한이 있다면 callback으로 onMapReady에 연결한다.


    @UiThread
    override fun onMapReady(naverMap: NaverMap){

        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
        this.naverMap = naverMap

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this) //gps 자동으로 받아오기
        setUpdateLocationListner() //내위치를 가져오는 코드

        // 지도가 클릭 되면 onMapClick() 콜백 메서드가 호출 되며, 파라미터로 클릭된 지점의 화면 좌표와 지도 좌표가 전달 된다.
        naverMap.setOnMapClickListener { point, coord ->
            marker(coord.latitude, coord.longitude)
        }

        // 지도가 롱 클릭 되면 onMapLongClick() 콜백 메서드가 호출 되며, 파라미터로 클릭된 지점의 화면 좌표와 지도 좌표가 전달 된다.
        naverMap.setOnMapLongClickListener { point, coord ->
            Toast.makeText(
                this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 클릭 된 지점의 좌표에 마커를 추가하는 함수
    private fun marker(latitude: Double, longitude: Double) {
        marker.position = LatLng(latitude, longitude)
        marker.map = naverMap

        getAddress(latitude, longitude)
    }

    // 클릭 된 지점의 좌표에 대한 주소를 구하는 함수
    private fun getAddress(latitude: Double, longitude: Double) {
        // Geocoder 선언
        val geocoder = Geocoder(applicationContext, Locale.KOREAN)

        // 반환 값에서 전체 주소만 사용한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                latitude, longitude, 1
            ) { address ->
                if (address.size != 0) {
                    toast(address[0].getAddressLine(0))
                }
            }
        } else {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                toast(addresses[0].getAddressLine(0))
            }
        }
    }

    // 주소 토스트
    private fun toast(text: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }


    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    //lateinit: 나중에 초기화 해주겠다는 의미

    @SuppressLint("MissingPermission")
    // 위치 업데이트
    fun setUpdateLocationListner() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
            interval = 1000 //1초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for ((i, location) in locationResult.locations.withIndex()) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
                    setLastLocation(location)
                }
            }
        }
        //location 요청 함수 호출 (locationRequest, locationCallback)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }//좌표계를 주기적으로 갱신

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)

        marker.position = myLocation

        marker.map = naverMap
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        //marker.map = null
    }

    // 스타벅스 위치 파악
    private fun readStarbucksLocations(): List<StarbucksLocation> {
        val locations = mutableListOf<StarbucksLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("starbucks.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split('|')
                if (tokens.size >= 7) {
                    val latitude = tokens[5].toDoubleOrNull()
                    val longitude = tokens[6].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        locations.add(StarbucksLocation(latitude, longitude))
                    }
                }
            }
        }

        return locations
    }

    // 스타벅스 마커 추가
    private fun addStarbucksMarkers() {
        if (starbucksMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }

        val starbucksLocations = readStarbucksLocations()
        starbucksLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 초록색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_starbucks)
            marker.map = naverMap
            starbucksMarkers.add(marker) // 리스트에 마커 추가
        }
    }

    // 스타벅스 마커 제거
    private fun removeStarbucksMarkers() {
        starbucksMarkers.forEach { it.map = null }
        starbucksMarkers.clear()
    }


    // 이디야 위치 파악
    private fun readEdiyaLocations(): List<EdiyaLocation> {
        val ediyalocations = mutableListOf<EdiyaLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("ediya.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        ediyalocations.add(EdiyaLocation(latitude, longitude))
                    }
                }
            }
        }

        return ediyalocations
    }

    // 이디야 마커 추가
    private fun addEdiyaMarkers() {
        if (ediyaMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }

        val composeLocations = readComposeLocations()
        composeLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 남색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_ediya)
            marker.map = naverMap
            ediyaMarkers.add(marker) // 리스트에 마커 추가
        }
    }

    // 이디야 마커 제거
    private fun removeEdiyaMarkers() {
        ediyaMarkers.forEach { it.map = null }
        ediyaMarkers.clear()
    }

    // 컴포즈 위치 파악
    private fun readComposeLocations(): List<ComposeLocation> {
        val composelocations = mutableListOf<ComposeLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("compose.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        composelocations.add(ComposeLocation(latitude, longitude))
                    }
                }
            }
        }

        return composelocations
    }


    // 컴포즈 마커 추가
    private fun addComposeMarkers() {
        if (composeMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }

        val composeLocations = readComposeLocations()
        composeLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 노란색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_compose)
            marker.map = naverMap
            composeMarkers.add(marker) // 리스트에 마커 추가
        }
    }

    // 컴포즈 마커 제거
    private fun removeComposeMarkers() {
        composeMarkers.forEach { it.map = null }
        composeMarkers.clear()
    }

    // 투썸 플레이스 위치 파악
    private fun readTwosomeLocations(): List<TwosomeLocation> {
        val twosomelocations = mutableListOf<TwosomeLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("twosome.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        twosomelocations.add(TwosomeLocation(latitude, longitude))
                    }
                }
            }
        }
        return twosomelocations
    }


    // 투썸 플레이스 마커 추가
    private fun addTwosomeMarkers() {
        if (twosomeMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }

        val twosomeLocations = readTwosomeLocations()
        twosomeLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 주황색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_twosome)
            marker.map = naverMap
            twosomeMarkers.add(marker) // 리스트에 마커 추가
        }
    }

    // 투썸 플레이스 마커 제거
    private fun removeTwosomeMarkers() {
        twosomeMarkers.forEach { it.map = null }
        twosomeMarkers.clear()
    }

    // 카페 게이트 위치 파악
    private fun readGateLocations(): List<GateLocation> {
        val gatelocations = mutableListOf<GateLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("gate.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        gatelocations.add(GateLocation(latitude, longitude))
                    }
                }
            }
        }
        return gatelocations
    }
    // 카페 게이트 마커 추가
    private fun addGateMarkers() {
        if (gateMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }
        val gateLocations = readGateLocations()
        gateLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 연두색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_gate)
            marker.map = naverMap
            gateMarkers.add(marker) // 리스트에 마커 추가
        }
    }
    // 카페 게이트 마커 제거
    private fun removeGateMarkers() {
        gateMarkers.forEach { it.map = null }
        gateMarkers.clear()
    }

    // 할리스 위치 파악
    private fun readHollysLocations(): List<HollysLocation> {
        val hollyslocations = mutableListOf<HollysLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("hollys.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        hollyslocations.add(HollysLocation(latitude, longitude))
                    }
                }
            }
        }
        return hollyslocations
    }
    // 할리스 마커 추가
    private fun addHollysMarkers() {
        if (hollysMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }
        val hollysLocations = readHollysLocations()
        hollysLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 하늘새 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_hollys)
            marker.map = naverMap
            hollysMarkers.add(marker) // 리스트에 마커 추가
        }
    }
    // 할리스 마커 제거
    private fun removeHollysMarkers() {
        hollysMarkers.forEach { it.map = null }
        hollysMarkers.clear()
    }

    // 매머드 커피 위치 파악
    private fun readMmthLocations(): List<MmthLocation> {
        val mmthlocations = mutableListOf<MmthLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("mmth.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        mmthlocations.add(MmthLocation(latitude, longitude))
                    }
                }
            }
        }
        return mmthlocations
    }
    // 매머드 커피 마커 추가
    private fun addMmthMarkers() {
        if (mmthMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }
        val mmthLocations = readMmthLocations()
        mmthLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 빨간색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_mmth)
            marker.map = naverMap
            mmthMarkers.add(marker) // 리스트에 마커 추가
        }
    }
    // 매머드 커피 마커 제거
    private fun removeMmthMarkers() {
        mmthMarkers.forEach { it.map = null }
        mmthMarkers.clear()
    }

    // 메가커피 위치 파악
    private fun readMegaLocations(): List<MegaLocation> {
        val megalocations = mutableListOf<MegaLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("mega.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        megalocations.add(MegaLocation(latitude, longitude))
                    }
                }
            }
        }
        return megalocations
    }
    // 메가커피 마커 추가
    private fun addMegaMarkers() {
        if (megaMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }
        val megaLocations = readMegaLocations()
        megaLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 연두색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_mega)
            marker.map = naverMap
            megaMarkers.add(marker) // 리스트에 마커 추가
        }
    }
    // 메가커피 마커 제거
    private fun removeMegaMarkers() {
        megaMarkers.forEach { it.map = null }
        megaMarkers.clear()
    }

    // 파스쿠찌 위치 파악
    private fun readPascucciLocations(): List<PascucciLocation> {
        val pascuccilocations = mutableListOf<PascucciLocation>()

        // assets 폴더 또는 적절한 파일 경로에서 CSV 파일 읽기
        val inputStream = assets.open("pascucci.csv") // 파일 경로 확인 필요
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val tokens = line.split(',')
                if (tokens.size >= 4) {
                    val latitude = tokens[2].toDoubleOrNull()
                    val longitude = tokens[3].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        pascuccilocations.add(PascucciLocation(latitude, longitude))
                    }
                }
            }
        }
        return pascuccilocations
    }
    // 파스쿠찌 마커 추가
    private fun addPascucciMarkers() {
        if (pascucciMarkers.isNotEmpty()) {
            return // 이미 마커가 있으면 추가하지 않음
        }
        val pascucciLocations = readPascucciLocations()
        pascucciLocations.forEach { location ->
            val marker = Marker()
            marker.position = LatLng(location.latitude, location.longitude)
            // 검은색 마커
            marker.icon = OverlayImage.fromResource(R.drawable.marker_pascucci)
            marker.map = naverMap
           pascucciMarkers.add(marker) // 리스트에 마커 추가
        }
    }
    // 파스쿠찌 마커 제거
    private fun removePascucciMarkers() {
        pascucciMarkers.forEach { it.map = null }
        pascucciMarkers.clear()
    }
}

// 스타벅스 매장 위치를 나타내는 데이터 클래스
data class StarbucksLocation(val latitude: Double, val longitude: Double)
// 각 매장 위치를 나타내는 데이터 클래스
data class EdiyaLocation(val latitude: Double, val longitude: Double)
data class ComposeLocation(val latitude: Double, val longitude: Double)
data class TwosomeLocation(val latitude: Double, val longitude: Double)
data class GateLocation(val latitude: Double, val longitude: Double)
data class HollysLocation(val latitude: Double, val longitude: Double)
data class MmthLocation(val latitude: Double, val longitude: Double)
data class MegaLocation(val latitude: Double, val longitude: Double)
data class PascucciLocation(val latitude: Double, val longitude: Double)
