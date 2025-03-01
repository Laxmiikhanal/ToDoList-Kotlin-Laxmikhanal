package com.example.ToDoApp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ToDoApp.R
import com.example.ToDoApp.model.ProductModel
import com.example.ToDoApp.repository.ProductRepistoryImpl
import com.example.ToDoApp.utils.LoadingUtils
import com.example.ToDoApp.viewmodel.ProductViewModel
import com.example.ToDoApp.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var loadingUtils: LoadingUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadingUtils = LoadingUtils(this)

        var repo = ProductRepistoryImpl()
        productViewModel = ProductViewModel(repo)
        binding.btnAddProduct.setOnClickListener {
            addProduct()
            this.finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun addProduct() {
        var productName = binding.editProductName.text.toString()
        var productDesc = binding.editProductDesc.text.toString()

        var model = ProductModel(
            "",
            productName,
            productDesc
        )

        productViewModel.addProduct(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
                loadingUtils.dismiss()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                loadingUtils.dismiss()
            }
        }
    }
}