package com.suhani.quizzify.models
import java.io.Serializable
data class Quizmodel (
    var amt:Int?=null,
    var time:Int?=null,
    var title:String?=null,
    var description:String?=null,
    var option1:String?=null,
    var option2:String?=null,
    var option3:String?=null,
    var option4:String?=null,
    var answer:String?=null,
    var userans:String?=null
):Serializable