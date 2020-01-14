package com.mansi.flupertask.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mansi.flupertask.entity.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE name LIKE :name")
    fun findByName(name: String): LiveData<Product>


    @Query("SELECT * FROM Product WHERE id =:productId")
    fun readProductByID(productId: Int): LiveData<Product>

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(vararg products: Product)

    @Query("SELECT * from product ORDER BY name ASC")
    fun getAlphabetizedWords(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()




}

