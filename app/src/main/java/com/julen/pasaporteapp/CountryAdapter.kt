package com.julen.pasaporteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter(private var countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivFlag: ImageView = view.findViewById(R.id.ivFlag)
        val tvName: TextView = view.findViewById(R.id.tvCountryName)
        val tvCapital: TextView = view.findViewById(R.id.tvCapital)
        val tvRegion: TextView = view.findViewById(R.id.tvRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]

        holder.tvName.text = country.name
        holder.tvCapital.text = country.capital ?: "Sin capital"
        holder.tvRegion.text = country.region

        // Cargar la imagen PNG de la bandera usando Glide
        Glide.with(holder.itemView.context)
            .load(country.flags.png)
            .into(holder.ivFlag)
    }

    override fun getItemCount(): Int = countries.size

    fun updateList(newList: List<Country>) {
        countries = newList
        notifyDataSetChanged()
    }
}
