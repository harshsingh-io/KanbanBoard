package com.codeenemy.kanbanboard.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.adapters.BoardItemsAdapter
import com.codeenemy.kanbanboard.databinding.ActivityMainBinding
import com.codeenemy.kanbanboard.databinding.AppBarMainBinding
import com.codeenemy.kanbanboard.firebase.FirestoreClass
import com.codeenemy.kanbanboard.model.Board
import com.codeenemy.kanbanboard.model.User
import com.codeenemy.kanbanboard.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }

    private var binding: ActivityMainBinding? = null
    private var bindingAppBar: AppBarMainBinding? = null

    //    private var bindingMainContent: ContentMainBinding? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    private var navUserName: TextView? = null
    private var navUserImage: ImageView? = null
    private var fabCreateBoard: FloatingActionButton? = null
    private lateinit var mUserName: String
    private var rvBoardsList: RecyclerView? = null
    private var tvNoBoardsAvailable: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingAppBar = AppBarMainBinding.inflate(layoutInflater)
//        bindingMainContent = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navUserName = findViewById(R.id.tv_username)
        navUserImage = findViewById(R.id.iv_user_image)
        fabCreateBoard = findViewById(R.id.fabCreateBoard)
        rvBoardsList = findViewById(R.id.rv_boards_list)
        tvNoBoardsAvailable = findViewById(R.id.tv_no_boards_available)
        navView?.setNavigationItemSelectedListener(this)
        setupActionBar()
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this, true)

        fabCreateBoard?.setOnClickListener {
            val intent = Intent(this, CreateBoardActivity::class.java)
            intent.putExtra(Constants.NAME, mUserName)
            startActivityForResult(intent, CREATE_BOARD_REQUEST_CODE)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == MY_PROFILE_REQUEST_CODE) {
            // Get the user updated details.
            FirestoreClass().loadUserData(this@MainActivity)
        } else if (resultCode == Activity.RESULT_OK
            && requestCode == CREATE_BOARD_REQUEST_CODE
        ) {
            // Get the latest boards list.
            FirestoreClass().getBoardsList(this@MainActivity)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivityForResult(
                    Intent(this, MyProfileActivity::class.java), MY_PROFILE_REQUEST_CODE
                )
            }

            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(
                    this@MainActivity, "Signed Out", Toast.LENGTH_SHORT
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

    fun updateNavigationUserDetails(user: User, readBoardList: Boolean) {
        hideProgressDialog()
        mUserName = user.name

        val userImageView =
            binding?.navView?.getHeaderView(0)?.findViewById<ImageView>(R.id.iv_user_image)
        val usernameTextView =
            binding?.navView?.getHeaderView(0)?.findViewById<TextView>(R.id.tv_username)
        usernameTextView?.text = user.name
        Glide.with(this).load(user.image).centerCrop().placeholder(R.drawable.ic_user_place_holder)
            .into(userImageView!!)

        if (readBoardList) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getBoardsList(this)
        }
    }

    fun populateBoardListToUI(boardList: ArrayList<Board>) {
        Log.e("MainActivity", "Received BoardList: $boardList")
        hideProgressDialog()
        if (boardList.size > 0) {
            rvBoardsList?.visibility = View.VISIBLE
            tvNoBoardsAvailable?.visibility = View.GONE

            rvBoardsList?.layoutManager = LinearLayoutManager(this@MainActivity)
            rvBoardsList?.setHasFixedSize(true)

            val adapter = BoardItemsAdapter(this@MainActivity, boardList)
            rvBoardsList?.adapter = adapter
            adapter.setOnClickListener(object :
                BoardItemsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Board) {
                    startActivity(Intent(this@MainActivity, TaskListActivity::class.java))
                }
            })
        } else {
            rvBoardsList?.visibility = View.GONE
            tvNoBoardsAvailable?.visibility = View.VISIBLE
        }
    }

}