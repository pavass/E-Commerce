package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Product
import com.example.myapplication.repos.ProductsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=inflater.inflate(R.layout.fragment_main,container,false)

//doAsync {
   // val json= URL("https://finepointmobile.com/data/products.json").readText()
  //  uiThread {

     //   val products= Gson().fromJson(json,Array<Product>::class.java).toList()
      //  root.recycler_view.apply {
          //  layoutManager= GridLayoutManager(activity,2) as RecyclerView.LayoutManager?
          //   adapter=ProductsAdapter(products)
       //    root.progressBar.visibility=View.GONE


       // }

   // }

//}

     //   root.progressBar.visibility=View.GONE
        val categories= listOf("Jeans","Shirts","Socks","Shoes","Tops","jackets","jeans","skirts","pants","gloves")
       root.categoriesRecyclerView.apply {
            layoutManager=LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
            adapter=CatagoriesAdapter(categories)
        }


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      val productsRepository=  ProductsRepository().getAllProducts()
        loadRecyclerView(productsRepository)
        searchButton.setOnClickListener {
            loadRecyclerView(ProductsRepository().searchForProducts(searchTerm.text.toString()))
        }

//        searchButton.setOnClickListener {
//            doAsync {
//                val db = Room.databaseBuilder(
//                    activity!!.applicationContext,
//                    AppDatabase::class.java, "database-name"
//                ).build()
//                val productsFromDatabase = db.productDao().searchFor("%${searchTerm.text}%")
//                val products=productsFromDatabase.map {
//                    Product(
//                        it.title,
//                        "https://finepointmobile.com/data/jeans2.jpg",
//                        it.price,
//                        true
//
//                    )
//                }
//
//                uiThread {
//                    recycler_view.apply {
//                        layoutManager=androidx.recyclerview.widget.GridLayoutManager(activity,2)
//                        adapter=ProductsAdapter(products)
//
//
//
//                    }
//                    progressBar.visibility=View.GONE
//                }
//            }
//        }

    }
    fun loadRecyclerView(productsRepository: Single<List<Product>>) {
        val single=productsRepository

        .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                d("pavas","success")
                recycler_view.apply {
                    layoutManager=androidx.recyclerview.widget.GridLayoutManager(activity,2)
                    adapter=ProductsAdapter(it){extraTitle,extraImageUrl,photoView->
                        val intent= Intent(activity,ProductDetails::class.java)
                        intent.putExtra("title",extraTitle)
                       // intent.putExtra("photo_url",extraImageUrl)
                        val options=ActivityOptionsCompat.makeSceneTransitionAnimation(activity as AppCompatActivity,photoView,"photoToAnimate")
                        startActivity(intent,options.toBundle())
                    }



                }
                progressBar.visibility=View.GONE
            },{
                d("pavas","error ${it.message}")
            })
    }
}