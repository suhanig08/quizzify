package com.suhani.quizzify.utils

import com.suhani.quizzify.R

object iconPicker {
    val icons = arrayOf(
        R.drawable.science_icon,
        R.drawable.geography_icon,
        R.drawable.sports_icon,
        R.drawable.music_icon,
        R.drawable.food_and_drink_icon,
        R.drawable.monument_icon,
        R.drawable.movies_icon
    )
    var currentIconIndex = -1
    fun getIcons(): Int {
        currentIconIndex = (currentIconIndex +1)%icons.size
        return icons[currentIconIndex]
    }
}