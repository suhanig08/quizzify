package com.suhani.quizzify.utils

object colorPicker {
    val colors = arrayOf(
        "#DF658E","#DC8E8E","#339933","#999900","#006666","#9999cc"
    )
    var currentColorIndex = 0
    fun getColor(): String {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}