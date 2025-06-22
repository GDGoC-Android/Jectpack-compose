package com.example.exampleM

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun deleteProduct(name: String) {
        productDao.deleteProduct(name)
    }

    suspend fun findProduct(name: String): List<Product> {
        return productDao.findProduct(name)
    }
}
