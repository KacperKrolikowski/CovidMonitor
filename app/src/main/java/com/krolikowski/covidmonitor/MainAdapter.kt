package com.krolikowski.covidmonitor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_card.view.*


class MainAdapter: RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    private var mAdapterCallback: AdapterCallback? = null

    inner class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                val position: Int = adapterPosition
                val countryFromRecyclerView: String = countryNames[position]
                mAdapterCallback?.onMethodCallback(countryFromRecyclerView)
            }
        }
    }

    val countryNames = listOf("World", "China", "India", "USA", "Indonesia")

    override fun getItemCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val single = layoutInflater.inflate(R.layout.country_card, parent, false)
        return CustomViewHolder(single)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.country_name.text = countryNames[position]
    }

    interface AdapterCallback {
        fun onMethodCallback(yourValue: String?)
    }
}



