package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat
import androidx.room.Room
import com.example.myapplication.cart.CartActivity
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.ProductFromDatabase

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        doAsync {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()
            db.productDao().insertAll(ProductFromDatabase(null,"socks-one dozen",1.99))
          val products=  db.productDao().getAll()
            uiThread {
                d("pavas","products size? ${products.size} ${products[0].title}")

            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MainFragment()).commit()

        navigationView.setNavigationItemSelectedListener {


            when(it.itemId){
                R.id.actionHome->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MainFragment()).commit()
                }
                R.id.actionJeans->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,jeansFragment()).commit()

                }

                R.id.actionShirts->{
                    d("pavas","Holographic Films was pressed")

                }
                R.id.actionAdmin->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,AdminFragment()).commit()

                }
            }
            it.isChecked=true
            drawerLayout.closeDrawers()
            true
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)//menu button on main activity action bar
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.actionCart){
            d("pavas","going to cart")
            startActivity(Intent(this,CartActivity::class.java))
            return true
        }
        drawerLayout.openDrawer(GravityCompat.START)
        return true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }
}
