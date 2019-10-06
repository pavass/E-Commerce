package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_row.view.*

class CatagoriesAdapter(private val categories: List<String>) : RecyclerView.Adapter<CatagoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.category_row,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount()=categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryName.text=categories[position]
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
val categoryName=view.categoryName

    }

}
