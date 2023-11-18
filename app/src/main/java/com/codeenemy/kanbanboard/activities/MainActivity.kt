package com.codeenemy.kanbanboard.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivityMainBinding
import com.codeenemy.kanbanboard.firebase.FirestoreClass
import com.codeenemy.kanbanboard.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityMainBinding? = null

    //    private lateinit var bindingNavHeader: NavHeaderMainBinding
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    private var navUserName: TextView? = null
    private var navUserImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        bindingNavHeader = NavHeaderMainBinding.bind(navView!!.getHeaderView(0))

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        setupActionBar()
        navView?.setNavigationItemSelectedListener(this)
        navUserName = findViewById(R.id.tv_user_name)
        navUserImage = findViewById(R.id.iv_user_image)
        FirestoreClass().loadUserData(this)

    }

    private fun setupActionBar() {
        val myToolbar: Toolbar = findViewById(R.id.toolbar_main_activity)
        setSupportActionBar(myToolbar)
        myToolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        myToolbar.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
        super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))
            }

            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(
                    this@MainActivity,
                    "Signed Out",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateNavigationUserDetails(user: User) {
        val userImageView =
            binding?.navView?.getHeaderView(0)?.findViewById<ImageView>(R.id.iv_user_image)
        val usernameTextView =
            binding?.navView?.getHeaderView(0)?.findViewById<TextView>(R.id.tv_username)
        usernameTextView?.text = user.name
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(userImageView!!)
    }
}