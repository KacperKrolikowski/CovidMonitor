package com.krolikowski.covidmonitor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IntroSliderAdapter(private val introSlides: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>() {


    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mTipView =
            view.findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.covid_tips)

        fun bind(introSlide: IntroSlide) {
            mTipView.setAnimation(introSlide.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }
}