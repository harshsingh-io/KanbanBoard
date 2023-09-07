package com.codeenemy.kanbanboard

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.codeenemy.kanbanboard.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private var binding: ActivityIntroBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        val typeFace: Typeface = Typeface.createFromAsset(assets, "charlie-display-bold.ttf")
        binding?.tvAppName?.typeface = typeFace

        binding?.btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding?.btnLogIn?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}

