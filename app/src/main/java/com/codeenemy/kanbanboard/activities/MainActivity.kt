package com.codeenemy.kanbanboard.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding:ActivityMainBinding? = null

    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        setupActionBar()
        navView?.setNavigationItemSelectedListener(this)
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
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            drawerLayout?.closeDrawer(GravityCompat.START)
        }else {
            doubleBackToExit()
        }
        super.onBackPressed()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_my_profile -> {
                Toast.makeText(
                    this@MainActivity,
                    "My Profile",
                    Toast.LENGTH_SHORT).show()
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(
                    this@MainActivity,
                    "Signed Out",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}