package com.suhani.quizzify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.suhani.quizzify.R
import com.suhani.quizzify.adapters.OptionAdapter
import com.suhani.quizzify.models.Question
import com.suhani.quizzify.models.Quiz

class QuestionActivity : AppCompatActivity() {

    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
      findViewById<Button>(R.id.btnPrevious).setOnClickListener{
          index--
          bindViews()
      }
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            index++
            bindViews()
        }
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            Log.d("FINAL QUIZ",questions.toString())

            val intent= Intent(this,ResultActivity::class.java)
            val json:String?= Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val genre: String? = intent.getStringExtra("GENRE")
        Log.d("Genera",genre.toString())
        if (genre != null) {
            firestore.collection("quizzes").whereEqualTo("title", genre)
                .get()
                .addOnSuccessListener {
                    if(it !=null && !it.isEmpty ) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        findViewById<Button>(R.id.btnPrevious).visibility = View.GONE
        findViewById<Button>(R.id.btnNext).visibility = View.GONE
        findViewById<Button>(R.id.btnSubmit).visibility = View.GONE

        if (index == 1) {
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            findViewById<Button>(R.id.btnSubmit).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
        } else {
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
        }

        val question: Question? = questions!!["question$index"]
        question?.let {
            findViewById<TextView>(R.id.description).text = it.description
            val optionAdapter = OptionAdapter(this, it)
            findViewById<RecyclerView>(R.id.optionlist).layoutManager = LinearLayoutManager(this)
            findViewById<RecyclerView>(R.id.optionlist).adapter = optionAdapter
            findViewById<RecyclerView>(R.id.optionlist).setHasFixedSize(true)

        }
    }
}