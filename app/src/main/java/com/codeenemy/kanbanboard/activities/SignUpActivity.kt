package com.codeenemy.kanbanboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
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
        binding?.btnSignUp?.setOnClickListener {
            registerUser()
        }
    }
    private fun registerUser() {
        val name: String = binding?.etName?.toString()!!.trim() {it <= ' '}
        val email: String = binding?.etEmail?.toString()!!.trim() {it <= ' '}
        val password: String = binding?.etPassword?.toString()!!.trim() {it <= ' '}
        if(validateForm(name, email, password)) {
            Toast.makeText(
                this,
                 "User Registered",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            } else -> {
                true
            }
        }
    }
}