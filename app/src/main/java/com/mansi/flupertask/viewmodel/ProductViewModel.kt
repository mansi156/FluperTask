package com.mansi.flupertask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mansi.flupertask.database.ProductRoomDatabase
import com.mansi.flupertask.entity.Product
import com.mansi.flupertask.repository.ProductRepository
import kotlinx.coroutines.launch


// Class extends AndroidViewModel and requires application as a parameter.
class ProductViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ProductRepository
    // LiveData gives us updated products when they change.
    val allProducts: LiveData<List<Product>>

    init {
        // Gets reference to ProductDao from RoomDatabase to construct
        // the correct ProductRepository.
        val productsDao = ProductRoomDatabase.getDatabase(application, viewModelScope).productDao()/*getDatabase(application).productDao()*/
        repository = ProductRepository(productsDao)
        allProducts = repository.allProducts
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun getProductById(productId: Int) = viewModelScope.launch {
        repository.getProductByName(productId)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.deleteFormList(product)
    }



}