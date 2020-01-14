package com.mansi.flupertask.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mansi.flupertask.R
import com.mansi.flupertask.entity.Product
import kotlinx.android.synthetic.main.dialog_add_product.*

/**
 * Created By Mansi Sharma
 */

class CreateProductActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var linearLayoutFirstImage: LinearLayout
    var colours = arrayOf("Color", "Red", "Blue", "Black", "Yellow", "Green", "Pink", "Orange")
    var spinner: Spinner? = null
    var selectedImage: String? = ONE_IMAGE
    var queryType: String? = ""
    var selectedColour: String? = ""
    var productId: Int = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_product)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val imgClose = findViewById<ImageView>(R.id.imgClose)
        linearLayoutFirstImage = findViewById(R.id.linearLayoutFirstImage)

        //Click Listener
        buttonSubmit.setOnClickListener(this)
        imgClose.setOnClickListener(this)
        linearLayoutFirstImage.setOnClickListener(this)
        linearLayoutSecondImage.setOnClickListener(this)
        linearLayoutThirdImage.setOnClickListener(this)


        spinner = this.spinnerColor
        spinner!!.setOnItemSelectedListener(this)
        queryType = intent.getStringExtra(QUERY_TYPE)

        //Set color spinner
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, colours)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(aa)

        if (queryType == UPDATE) {
            setViewOnUpdate()
        }

    }


    /**
     * Set View in case of update
     * */
    fun setViewOnUpdate() {
        intent?.getParcelableExtra<Product>(PRODUCT_DETAIL)?.let {
            val product = intent.getParcelableExtra<Product>(PRODUCT_DETAIL)
            editTextProductName?.setText(product.name)
            editTextDescription?.setText(product.description)
            editTextPrice?.setText(product.regularPrice)
            productId = product.id
            setColor(product)
        }
    }

    /**
     * Click Listener
     *
     * */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonSubmit -> {
                if (validationOnAdding()) {
                    //Get Intent from Home Screen
                    val replyIntent = Intent()
                    if (TextUtils.isEmpty(editTextProductName.text)) {
                        setResult(Activity.RESULT_CANCELED, replyIntent)
                    } else {
                        val productName = editTextProductName.text.toString()
                        val productDescription = editTextDescription.text.toString()
                        val productRegularPrice = "$ " + editTextPrice.text.toString()
                        val saleProduct = editTextPrice.text.toString().trim().toInt()
                        val productSalesPrice = "$ " + (saleProduct - 20)
                        val product = Product()
                        if (queryType == UPDATE) {
                            product.id = productId
                        }
                        product.name = productName
                        product.description = productDescription
                        product.regularPrice = editTextPrice.text.toString()
                        product.salesPrice = productSalesPrice
                        product.selectedColour = selectedColour
                        product.productImage =
                            selectedImage                    // Image can be 3 so I put the condition for these image using one variable
                        //Add Colour list in product model
                        val list: ArrayList<String> = ArrayList()
                        list.addAll(colours)
                        product.colors = list

                        replyIntent.putExtra(PRODUCT_DETAIL, product)
                        replyIntent.putExtra(QUERY_TYPE, queryType)
                        setResult(Activity.RESULT_OK, replyIntent)
                    }
                    finish()
                }

            }
            R.id.linearLayoutFirstImage -> {
                selectedImage = ONE_IMAGE
                linearLayoutFirstImage.setBackgroundResource(R.drawable.product_selection_background)
                linearLayoutSecondImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
                linearLayoutThirdImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
            }
            R.id.linearLayoutSecondImage -> {

                selectedImage = TWO_IMAGE
                linearLayoutSecondImage.setBackgroundResource(R.drawable.product_selection_background)
                linearLayoutFirstImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
                linearLayoutThirdImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
            }
            R.id.linearLayoutThirdImage -> {

                selectedImage = THREE_IMAGE
                linearLayoutThirdImage.setBackgroundResource(R.drawable.product_selection_background)
                linearLayoutSecondImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
                linearLayoutFirstImage.setBackgroundResource(android.R.drawable.screen_background_light_transparent)
            }
            R.id.imgClose -> {
                finish()
            }
            else -> {
                // else condition
            }
        }
    }


    /**
     * Spinner selction callback
     * */
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        selectedColour = colours[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    /**
     * Constant values
     * */

    companion object {
        const val PRODUCT_DETAIL = "PRODUCT_DETAIL"
        const val QUERY_TYPE = "QUERY_TYPE"
        const val UPDATE = "UPDATE"
        const val INSERT = "INSERT"
        const val ONE_IMAGE = "ONE_IMAGE"
        const val TWO_IMAGE = "TWO_IMAGE"
        const val THREE_IMAGE = "THREE_IMAGE"
    }

    /**
     * Validation on create product
     * */
    fun validationOnAdding(): Boolean {

        if (editTextProductName.text.toString().trim().isEmpty()) {
            editTextProductName.snack("Please enter product name")
            return false
        } else if (editTextDescription.text.toString().trim().isEmpty()) {
            editTextDescription.snack("Please enter product description")
            return false
        } else if (editTextPrice.text.toString().trim().isEmpty()) {
            editTextPrice.snack("Please enter product price")
            return false
        } else if (selectedColour.equals("") || selectedColour.equals("Color")) {
            editTextPrice.snack("Please select colour")
            return false
        }
        return true

    }


    /**
     * Snack Bar method for Alert
     * */
    fun View.snack(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }


    /**
     * Set colour in spinner when comes from update type
     * */
    fun setColor(product: Product) {
        for (position in 0 until colours.size) {
            if (colours.get(position) == product.selectedColour) {
                spinner?.setSelection(position)
                return
            }
        }

    }
}