package com.krolikowski.covidmonitor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_card.view.*

class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return 222
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val single = layoutInflater.inflate(R.layout.country_card, parent, false)
        return CustomViewHolder(single)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.country_name.text = "Hejka"
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}