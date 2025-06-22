package com.example.exampleM

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    val id: Int = 0,

    @ColumnInfo(name = "productName")
    val productName: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int
)

