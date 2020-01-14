package com.mansi.flupertask.adpter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mansi.flupertask.R
import com.mansi.flupertask.activity.CreateProductActivity
import com.mansi.flupertask.activity.HomeScreenActivity
import com.mansi.flupertask.entity.Product
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlin.coroutines.coroutineContext

class ProductListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<Product>() // Cached copy of products
    private val activity: HomeScreenActivity = context as HomeScreenActivity

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textViewProductName)
        val relativeLayoutCompleteView: RelativeLayout =
            itemView.findViewById(R.id.relativeLayoutCompleteView)
        val imageViewEdit: ImageView = itemView.findViewById(R.id.imageViewEdit)
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
        val imageProductImage: ImageView = itemView.findViewById(R.id.imageProductImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = products[position]
        holder.wordItemView.text = current.name

        when (current.productImage) {
            CreateProductActivity.ONE_IMAGE -> holder.imageProductImage.setImageResource(R.drawable.top_one)
            CreateProductActivity.TWO_IMAGE ->  holder.imageProductImage.setImageResource(R.drawable.top_two)
            CreateProductActivity.THREE_IMAGE ->  holder.imageProductImage.setImageResource(R.drawable.top_three)

        }


        holder.relativeLayoutCompleteView.setOnClickListener {
            activity.productDeatilCallback(products[position])
        }

        holder.imageViewEdit.setOnClickListener {
            activity.updateCallback(products[position])
        }

        holder.imageViewDelete.setOnClickListener {
            activity.deleteCallback(products[position])
        }
    }

    internal fun setWords(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun getItemCount() = products.size
}