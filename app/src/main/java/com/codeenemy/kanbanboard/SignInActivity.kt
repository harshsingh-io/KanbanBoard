package com.codeenemy.kanbanboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codeenemy.kanbanboard.databinding.ActivitySignInBinding
import com.codeenemy.kanbanboard.databinding.ActivitySignUpBinding
import com.codeenemy.kanbanboard.databinding.SignInActivityBinding

class SignInActivity : AppCompatActivity() {
    private var binding: ActivitySignInBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBar()
    }
    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarSignInActivity)
        val actionBar = supportActionBar
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding?.toolbarSignInActivity?.setNavigationOnClickListener { onBackPressed() }
    }
}