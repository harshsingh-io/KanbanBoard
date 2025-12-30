package com.codeenemy.kanbanboard.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codeenemy.kanbanboard.R
import com.codeenemy.kanbanboard.databinding.ActivityCreateBoardBinding
import com.codeenemy.kanbanboard.firebase.FirestoreClass
import com.codeenemy.kanbanboard.model.Board
import com.codeenemy.kanbanboard.model.User
import com.codeenemy.kanbanboard.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class CreateBoardActivity : BaseActivity() {
    private var binding: ActivityCreateBoardBinding? = null
    private var mSelectedImageFileUri: Uri? = null
    private lateinit var mUserName: String
    private var mBoardImageURL: String = ""
    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBoardBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBar()

        if (intent.hasExtra(Constants.NAME)) {
            mUserName = intent.getStringExtra(Constants.NAME).toString()
        }


        binding?.ivBoardImage?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }
        binding?.btnCreate?.setOnClickListener {
            createBoardAction()
        }

        binding?.etBoardName?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                createBoardAction()
                true
            } else {
                false
            }
        }
    }

    private fun createBoardAction() {
        if (mSelectedImageFileUri != null) {
            uploadBoardImage()
        } else {
            showProgressDialog(resources.getString(R.string.please_wait))
            createBoard()
        }
    }

    private fun createBoard() {
        val assignedUserArrayList: ArrayList<String> = ArrayList()
        assignedUserArrayList.add(getCurrentUserID())

        var board = Board(
            binding?.etBoardName?.text.toString(), mBoardImageURL, mUserName, assignedUserArrayList
        )
        FirestoreClass().createBoard(this, board)
    }

    /**
     * A function to upload the selected user image to firebase cloud storage.
     */
    private fun uploadBoardImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        if (mSelectedImageFileUri != null) {

            //getting the storage reference
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "BOARD_IMAGE" + System.currentTimeMillis() + "." + Constants.getFileExtension(
                    this, mSelectedImageFileUri
                )
            )

            //adding the file to reference
            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
                    // The image upload is success
                    Log.e(
                        "Board Image URL",
                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    )

                    // Get the downloadable url from the task snapshot
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                            Log.e("Downloadable Image URL", uri.toString())

                            // assign the image url to the variable.
                            mBoardImageURL = uri.toString()
                            hideProgressDialog()

                            // Call a function to update user details in the database.
                            createBoard()
                        }
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        this, exception.message, Toast.LENGTH_LONG
                    ).show()

                    hideProgressDialog()
                }
        }
    }

    /**
     * A function to update the user profile details into the database.
     */
    private fun updateBoardData() {

        val userHashMap = HashMap<String, Any>()
//        var anyChangesMade = false

        if (mBoardImageURL.isNotEmpty() && mBoardImageURL != mBoardDetails.image) {
            userHashMap[Constants.IMAGE] = mBoardImageURL
//            anyChangesMade = true
        }

        if (binding?.etBoardName?.text.toString() != mBoardDetails.name) {
            userHashMap[Constants.NAME] = binding?.etBoardName?.text.toString()
//            anyChangesMade = true

        }

//        if (binding?.etMobile?.text.toString() != mUserDetails.mobile.toString()) {
//            userHashMap[Constants.MOBILE] = binding?.etMobile?.text.toString().toLong()
////            anyChangesMade = true
//        }

        // Update the data in the database.
        FirestoreClass().updateBoardData(this, userHashMap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null) {
            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data

            try {
                // Load the user image in the ImageView.
                Glide.with(this)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_board_place_holder) // A default place holder
                    .into(binding?.ivBoardImage!!) // the view in which the image will be
                // loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding?.toolbarCreateBoardActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.create_board)
        }

        binding?.toolbarCreateBoardActivity?.setNavigationOnClickListener { onBackPressed() }
    }

    fun boardCreatedSuccessfully() {
        hideProgressDialog()
        finish()
    }

    fun boardUpdateSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }
}