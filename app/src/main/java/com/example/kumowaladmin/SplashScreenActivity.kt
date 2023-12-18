package com.example.kumowaladmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kumowaladmin.`object`.Pengguna
import com.example.kumowaladmin.`object`.RQ
import com.example.kumowaladmin.`object`.SP

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        SP.initLoginSP(this)
        RQ.initRQ(this)

        if (SP.isLogin()){
            Pengguna.getDetailFromDb(SP.getUserId()) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}