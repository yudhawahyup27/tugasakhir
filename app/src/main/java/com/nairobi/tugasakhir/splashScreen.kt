package com.nairobi.tugasakhir

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login_page_admin.*

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setFullscreen()
        setVersion()
        setTransition()
    }

    private fun setFullscreen() {
        //Menyembunyikan action bar
        supportActionBar?.hide()
        //Mengatur layout menjadi Full Screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setVersion() {
        val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val versionNow = packageInfo.versionName
        val tvVersion = findViewById<TextView>(R.id.tv_version)
        tvVersion.text = "Versi $versionNow"
    }

    private fun setTransition() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }, 3000L) //delay 3 second
    }
}