package com.codeenemy.kanbanboard.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivitySignInBinding
import com.codeenemy.kanbanboard.firebase.FirestoreClass
import com.codeenemy.kanbanboard.model.User
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : BaseActivity() {
    private var binding: ActivitySignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnSignIn?.setOnClickListener {
            signInRegisteredUser()
        }

        binding?.etPassword?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding?.btnSignIn?.performClick()
                true
            } else {
                false
            }
        }
        setupActionBar()

    }

    private fun signInRegisteredUser() {
        val email: String = binding?.etEmail?.text.toString().trim() { it <= ' ' }
        val password: String = binding?.etPassword?.text.toString().trim() { it <= ' ' }
        if (validateForm(email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        FirestoreClass().loadUserData(this@SignInActivity)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Registration failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email")
                false
            }

            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }

            else -> {
                true
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarSignInActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        binding?.toolbarSignInActivity?.setNavigationOnClickListener { onBackPressed() }
    }

    fun signInSuccess(user: User) {
        hideProgressDialog()
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
    }

}