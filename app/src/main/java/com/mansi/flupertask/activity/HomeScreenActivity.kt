package com.mansi.flupertask.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mansi.flupertask.R
import com.mansi.flupertask.adpter.ProductListAdapter
import com.mansi.flupertask.entity.Product
import com.mansi.flupertask.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Mansi Sharma
 * */
class HomeScreenActivity : AppCompatActivity(), View.OnClickListener {
    private val newWordActivityRequestCode = 1
    private lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerView: RecyclerView
    var b: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerview)
        val buttonShowProduct = findViewById<Button>(R.id.buttonShowProduct)
        val buttonCreateProduct = findViewById<Button>(R.id.buttonCreateProduct)

        // Set Product Adapter
        val adapter = ProductListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        productViewModel =
            ViewModelProviders.of(this@HomeScreenActivity).get(ProductViewModel::class.java)
        productViewModel.allProducts.observe(this, Observer { products ->
            // Update the cached copy of the words in the adapter.
            products?.let {
                adapter?.setWords(it)
            }
        })

        // Button Clicks
        buttonCreateProduct.setOnClickListener(this)
        buttonShowProduct.setOnClickListener(this)
    }

    /**
     * onClick callback
     * */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonSubmit -> {
                val intent = Intent(this@HomeScreenActivity, CreateProductActivity::class.java)
                val product = Product()
                intent.putExtra(CreateProductActivity.PRODUCT_DETAIL, product)
                intent.putExtra(CreateProductActivity.QUERY_TYPE, CreateProductActivity.INSERT)
                startActivityForResult(intent, newWordActivityRequestCode)
            }
            R.id.buttonShowProduct -> {
                visibilityOfShowButton()
            }
        }
    }


    /**
     * Getting Data from Create Product Activity
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Product>(CreateProductActivity.PRODUCT_DETAIL)?.let {
                val product = data.getParcelableExtra<Product>(CreateProductActivity.PRODUCT_DETAIL)
                val queryType = data.getStringExtra(CreateProductActivity.QUERY_TYPE)
                if (queryType == CreateProductActivity.INSERT) {
                    productViewModel.insert(product)
                    buttonCreateProduct.snack(product.name + "has been added to database")
                } else {
                    productViewModel.update(product)
                    buttonCreateProduct.snack(product.name + "has been updated to database")
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Visibility of Show Button
     * */
    fun visibilityOfShowButton() {
        if (b == true) {
            recyclerView.visibility = View.VISIBLE
            buttonShowProduct.text = getString(R.string.hide_product)
            b = false
        } else if (b == false) {
            recyclerView.visibility = View.GONE
            buttonShowProduct.text = getString(R.string.show_product)
            b = true
        }
    }


    /**
     * Update Product Callback
     * */
    fun updateCallback(product: Product) {
        val intent = Intent(this, CreateProductActivity::class.java)
        intent.putExtra(CreateProductActivity.PRODUCT_DETAIL, product)
        intent.putExtra(CreateProductActivity.QUERY_TYPE, CreateProductActivity.UPDATE)
        startActivityForResult(intent, newWordActivityRequestCode)
    }


    /**
     * Product Detail Callback
     * */
    fun productDeatilCallback(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(CreateProductActivity.PRODUCT_DETAIL, product)
        startActivity(intent)
    }

    /**
     * Delete callback
     * */
    fun deleteCallback(product: Product) {
        productViewModel.deleteProduct(product)
    }


    /**
     * Snackbar Alert
     * */
    fun View.snack(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }


}

