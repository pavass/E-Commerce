package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.repos.ProductsRepository
import com.squareup.picasso.Picasso
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.product_details.*

class ProductDetails: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.product_details)
        val title=intent.getStringExtra("title")
      //  val photoUrl=intent.getStringExtra("photo_url")
        val product=ProductsRepository().getProductByName(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                product_name.text=it.title
                Picasso.get().load(it.photoUrl).into(photo)
                thePriceOfproduct.text="$${it.price}"
            },{})

        availability.setOnClickListener {
            AlertDialog.Builder(this).setMessage("ITS IN STOCK").setPositiveButton("OK"
            ) { dialog, which -> }.create().show()
        }
    }
}