package com.example.myapplication.repos

import com.example.myapplication.model.Product
import com.google.gson.Gson
import io.reactivex.Single
import java.net.URL

class ProductsRepository {
    fun getAllProducts(): Single<List<Product>> {
        return Single.create<List<Product>>{

            it.onSuccess(fetchProducts())

        }
    }
    fun searchForProducts(term:String):Single<List<Product>>{
        return Single.create<List<Product>>{

            val filteredProducts=fetchProducts().filter { it.title.contains(term,true) }
            it.onSuccess(filteredProducts)
        }
    }
    fun getProductByName(name:String):Single<Product>{
       return Single.create<Product>{
           val product= fetchProducts().first { it.title == name }
           it.onSuccess(product)
        }
    }
    fun fetchProducts():List<Product>{
        val json=  URL("https://gist.githubusercontent.com/pavass/ab34d212b70f2f5fa68d91cb8f3f09f8/raw/76125faeb98ad036b87c1f8dfcc70965a1bec29f/shopping_products.json").readText()
        return  Gson().fromJson(json,Array<Product>::class.java).toList()
    }
}