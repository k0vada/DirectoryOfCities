package com.example.lab13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter (private val cities: List<City>, private val onItemClick: (City) -> Unit) : RecyclerView.Adapter<CityAdapter.CityViewHolder>()
{
    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textCity)
        val textViewRegion: TextView = itemView.findViewById(R.id.textReg)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(cities[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = cities[position]
        holder.textViewTitle.text = currentItem.title
        holder.textViewRegion.text = currentItem.region
    }

    override fun getItemCount() = cities.size
}