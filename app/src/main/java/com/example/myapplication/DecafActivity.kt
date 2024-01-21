package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DecafActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.decaf)
        var btnMenu : ImageButton = findViewById(R.id.nav_menu)

        btnMenu.setOnClickListener {
            finish()
        }

        // 기존에 있던 맵 버튼 관련 코드 (변경하지 않음)
        val btnMap: ImageButton = findViewById(R.id.nav_map)
        btnMap.setOnClickListener {
            // 맵 버튼 클릭 시 MapActivity로 화면 전환
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}