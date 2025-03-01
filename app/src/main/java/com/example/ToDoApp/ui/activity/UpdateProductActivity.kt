package com.example.ToDoApp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ToDoApp.R
import com.example.ToDoApp.databinding.ActivityUpdateProductBinding
import com.example.ToDoApp.model.ProductModel
import com.example.ToDoApp.repository.ProductRepistoryImpl
import com.example.ToDoApp.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProductBinding

    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepistoryImpl()
        productViewModel = ProductViewModel(repo)


        //if intent bata ako model ko value get garnu paryo bhane
        var products: ProductModel? = intent.getParcelableExtra("products")

        products.let {
            binding.updateProductDesc.setText(it?.taskDesc.toString())

            binding.updateProductName.setText(it?.taskTitle.toString())
        }

        var productId: String? = intent.getStringExtra("productId")


        productViewModel.getProductById(productId.toString())

        productViewModel.products.observe(this) {
            binding.updateProductDesc.setText(it?.taskDesc.toString())
            binding.updateProductName.setText(it?.taskTitle.toString())
        }

        binding.btnUpdate.setOnClickListener {
            val productName = binding.updateProductName.text.toString()
            val desc = binding.updateProductDesc.text.toString()

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["productName"] = productName
            updatedMap["productDesc"] = desc

            productViewModel.updateProduct(
                productId.toString(),
                updatedMap
            ) { success, message ->
                if (success) {
                    Toast.makeText(this@UpdateProductActivity,
                        message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateProductActivity,
                        message, Toast.LENGTH_LONG).show()

                }
            }
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}