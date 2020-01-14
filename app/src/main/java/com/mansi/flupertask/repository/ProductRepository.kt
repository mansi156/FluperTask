package com.mansi.flupertask.repository

import androidx.lifecycle.LiveData
import com.mansi.flupertask.dao.ProductDao
import com.mansi.flupertask.entity.Product

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ProductRepository(private val productDao: ProductDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allProducts: LiveData<List<Product>> = productDao.getAll()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    fun getProductByName (productId: Int) {
        val product : LiveData<Product> = productDao.readProductByID(productId)
    }

    suspend fun deleteFormList (product: Product) {
        productDao.deleteProduct(product)
    }

}