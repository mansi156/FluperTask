package com.mansi.flupertask.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "product")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") var id: Int = 0,
    @ColumnInfo(name = "Name") var name: String? = "",
    @ColumnInfo(name = "Description") var description: String? = "",
    @ColumnInfo(name = "RegularPrice") var regularPrice: String? = "",
    @ColumnInfo(name = "SalesPrice") var salesPrice: String? = "",
    @ColumnInfo(name = "ProductImage") var productImage: String? = "",
    @ColumnInfo(name = "SelectedColour") var selectedColour: String? = "",
    @ColumnInfo(name = "colors") var colors: List<String>? = arrayListOf()
) : Parcelable
