package com.example.exampleM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val allProducts: LiveData<List<Product>>
    val searchResults = MutableLiveData<List<Product>>()
    private val repository: ProductRepository

    init {
        val productDao = ProductRoomDatabase.getInstance(application).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product)
        }
    }

    fun findProduct(name: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                repository.findProduct(name)
            }
            searchResults.value = results
        }
    }

    fun deleteProduct(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(name)
        }
    }
}
