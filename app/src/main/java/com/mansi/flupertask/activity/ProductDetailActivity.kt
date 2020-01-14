package com.mansi.flupertask.activity

import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mansi.flupertask.R
import com.mansi.flupertask.entity.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var textViewProductName: TextView
    private lateinit var textViewDescriptionDetail: TextView
    private lateinit var textViewPRegularPrice: TextView
    private lateinit var textViewSalePrice: TextView
    private lateinit var imgClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        textViewProductName = findViewById(R.id.textViewProductName)
        textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail)
        textViewPRegularPrice = findViewById(R.id.textViewPRegularPrice)
        textViewSalePrice = findViewById(R.id.textViewSalePrice)
        imgClose = findViewById(R.id.imgClose)


        intent?.getParcelableExtra<Product>(CreateProductActivity.PRODUCT_DETAIL)?.let {
            val product = intent.getParcelableExtra<Product>(CreateProductActivity.PRODUCT_DETAIL)
            setProductView(product)
        }

        imgClose.setOnClickListener { finish() }

    }

    /**
     * Set Product Detail View
     * */
    fun setProductView(product: Product) {
        textViewProductName.text = product.name
        textViewDescriptionDetail.text = product.description
        textViewPRegularPrice.text = "$ " + product.regularPrice
        textViewSalePrice.text = product.salesPrice
        textViewColorName.text = product.selectedColour
        textViewPRegularPrice.paintFlags =
            textViewPRegularPrice.paintFlags or STRIKE_THRU_TEXT_FLAG


        product.selectedColour?.let { setColor(it) }

        when (product.productImage) {
            CreateProductActivity.ONE_IMAGE -> imageViewProductImage.setImageResource(R.drawable.top_one)
            CreateProductActivity.TWO_IMAGE -> imageViewProductImage.setImageResource(R.drawable.top_two)
            CreateProductActivity.THREE_IMAGE -> imageViewProductImage.setImageResource(R.drawable.top_three)

        }
    }

    /**
     * Set product color
     * */

    fun setColor(selectedColor: String) {
        when (selectedColor) {
            "Red" -> textViewColorName.setTextColor(Color.parseColor("#ff0000"))
            "Blue" -> textViewColorName.setTextColor(Color.parseColor("#0066cc"))
            "Black" -> textViewColorName.setTextColor(Color.parseColor("#000000"))
            "Yellow" -> textViewColorName.setTextColor(Color.parseColor("#ffff66"))
            "Green" -> textViewColorName.setTextColor(Color.parseColor("#00cc66"))
            "Pink" -> textViewColorName.setTextColor(Color.parseColor("#ff99cc"))
            "Orange" -> textViewColorName.setTextColor(Color.parseColor("#ff9966"))
            else -> {
                textViewColorName.setTextColor(Color.parseColor("#ffffff"))
            }
        }

    }
}

