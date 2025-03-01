package com.example.ToDoApp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ToDoApp.R
import com.example.ToDoApp.model.ProductModel
import com.example.ToDoApp.ui.activity.UpdateProductActivity
//import com.example.ToDoApp.R
import java.util.ArrayList

class ProductAdapter(val context: Context,
                     var data : ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){


        val editButton : TextView = itemView.findViewById(R.id.lblEdit)
        val pName : TextView = itemView.findViewById(R.id.displayName)

        val pDesc : TextView = itemView.findViewById(R.id.displayDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView : View = LayoutInflater.from(context).
        inflate(R.layout.sample_products,parent,false)

        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.pName.text = data[position].taskTitle
        holder.pDesc.text = data[position].taskDesc


        holder.editButton.setOnClickListener {
            val intent = Intent(context,UpdateProductActivity::class.java)
//            if model pass garnu paryo bhane
//            first make model parcelable
//            intent.putExtra("products",data[position])

            intent.putExtra("productId",data[position].taskId)

            context.startActivity(intent)
        }
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    fun getProductId(position: Int) : String{
        return data[position].taskId
    }

}