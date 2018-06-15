package com.example.keviniswara.bookinglapang

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.keviniswara.bookinglapang.login.view.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}