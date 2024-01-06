package com.suhani.quizzify.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.google.gson.Gson
import com.suhani.quizzify.R
import com.suhani.quizzify.models.Question
import com.suhani.quizzify.models.Quiz

class ResultActivity : AppCompatActivity() {

    lateinit var quiz:Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData:String?=intent.getStringExtra("QUIZ")
        quiz= Gson().fromJson<Quiz>(quizData,Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
       val builder=StringBuilder("")
        for(entry:MutableMap.MutableEntry<String,Question> in quiz.questions.entries){
            val question:Question=entry.value
            builder.append("<font color='#18206F'><b>Questions: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
            findViewById<TextView>(R.id.txtAnswer).text= Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT);
        }else{
            findViewById<TextView>(R.id.txtAnswer).text=Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score=0
        for (entry:MutableMap.MutableEntry<String,Question> in quiz.questions.entries){
              val question:Question=entry.value
            if(question.answer==question.userans) {
                score += 10
            }
        }
        findViewById<TextView>(R.id.txtScore).text="Your Score: $score"
    }
}