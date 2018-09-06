package com.github.valkoz.kogma

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.valkoz.kogma.model.TransformedItem

class ItemAdapter(private var dataset : List<TransformedItem>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
        val pubDate: TextView = v.findViewById(R.id.pub_date)
        val creator: TextView = v.findViewById(R.id.creator)
        val description: TextView = v.findViewById(R.id.description)
        val categories: TextView = v.findViewById(R.id.categories)
    }

    fun addItems(items: List<TransformedItem>) {
        dataset = items
        notifyDataSetChanged()
    }

    fun getItems(): List<TransformedItem> {
        return dataset
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataset[position].title
        holder.pubDate.text = dataset[position].pubDate
        holder.creator.text = dataset[position].creator
        holder.categories.text = dataset[position].categories
        holder.description.text = dataset[position].description
    }
}