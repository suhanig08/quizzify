package com.suhani.quizzify.models

data class Quiz(
    var title:String="",
    var id:String="",
    var questions: MutableMap<String,Question> = mutableMapOf()
)
