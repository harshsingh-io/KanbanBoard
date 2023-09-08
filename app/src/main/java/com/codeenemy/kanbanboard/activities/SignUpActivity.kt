package com.codeenemy.kanbanboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarSignUpActivity)
        val actionBar = supportActionBar
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding?.toolbarSignUpActivity?.setNavigationOnClickListener { onBackPressed() }
    }
}