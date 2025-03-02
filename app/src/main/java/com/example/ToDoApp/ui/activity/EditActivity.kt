package com.example.ToDoApp.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ToDoApp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var profileImage: ImageView
    private lateinit var btnSave: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var db: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Initialize UI components
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        profileImage = findViewById(R.id.profileImage)
        btnSave = findViewById(R.id.btnSave)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        db = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("profile_pictures")

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Updating profile...")

        // Load user data
        loadUserData()

        // Save button action
        btnSave.setOnClickListener {
            updateProfile()
        }
    }

    private fun loadUserData() {
        db.collection("users").document(user.uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val name = document.getString("name")
                val email = document.getString("email")
                val imageUrl = document.getString("imageUrl")

                editName.setText(name)
                editEmail.setText(email)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProfile() {
        val name = editName.text.toString().trim()
        val email = editEmail.text.toString().trim()

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.show()

        val userUpdates = mapOf(
            "name" to name,
            "email" to email
        )

        db.collection("users").document(user.uid).update(userUpdates)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
    }
}
