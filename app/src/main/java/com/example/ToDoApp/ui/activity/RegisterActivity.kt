package com.example.ToDoApp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ToDoApp.R
import com.example.ToDoApp.databinding.ActivityRegisterBinding
import com.example.ToDoApp.model.UserModel
import com.example.ToDoApp.repository.UserRepositoryImpl
import com.example.ToDoApp.utils.LoadingUtils
import com.example.ToDoApp.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    lateinit var userViewModel: UserViewModel

    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val userRepository = UserRepositoryImpl(FirebaseAuth.getInstance())

        userViewModel = UserViewModel(userRepository)

        loadingUtils = LoadingUtils(this)

        binding.signUp.setOnClickListener {
            loadingUtils.show()
            var email: String = binding.registerEmail.text.toString()
            var password: String = binding.registerPassword.text.toString()
            var fName: String = binding.registerFname.text.toString()
            var lName: String = binding.registerLName.text.toString()
            var address: String = binding.registerAddress.text.toString()
            var contact: String = binding.registerContact.text.toString()

            userViewModel.signup(email,password){
                success,message,userId ->
                if(success){
                    val userModel = UserModel(
                        userId,
                        email, fName, lName, address, contact
                    )
                    addUser(userModel)
                }else{
                    loadingUtils.dismiss()
                    Toast.makeText(this@RegisterActivity,
                        message,Toast.LENGTH_SHORT).show()
                }


            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun addUser(userModel: UserModel){
        userViewModel.addUserToDatabase(userModel.userId,userModel){
                success,message ->
            if(success){
                Toast.makeText(this@RegisterActivity
                    ,message,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@RegisterActivity
                    ,message,Toast.LENGTH_SHORT).show()
            }
            loadingUtils.dismiss()
        }
    }
}