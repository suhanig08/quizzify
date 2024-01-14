package com.suhani.quizzify.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.suhani.quizzify.R
import com.suhani.quizzify.adapters.QuizAdapter
import com.suhani.quizzify.models.Quiz


class startQuiz : AppCompatActivity() {
    private lateinit var catname:TextView
    private lateinit var questionNo:TextView
    private lateinit var bestscr:TextView
    private lateinit var totaltime:TextView
    private lateinit var startquiz:Button
    private lateinit var backB:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_test)
        //setUpFirestore()
        initializeit()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun initializeit() {
        catname=findViewById(R.id.category)
        questionNo=findViewById(R.id.questionnumber)
        bestscr=findViewById(R.id.bestscore)
        totaltime=findViewById(R.id.time)
        startquiz=findViewById(R.id.startquizbtn)
        backB=findViewById(R.id.startquizbackbtn)



        backB.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        startquiz.setOnClickListener {
            val intent= Intent(this,QuestionActivity::class.java)
            var genera = intent.getStringExtra("GENRE")


                Log.d("Genera", genera.toString())

            intent.putExtra("GENRE",genera)
            startActivity(intent)
        }
    }
    private fun setUpFirestore() {
        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        firestore.collection("quizzes").whereEqualTo("title",catname)
            .get()

        }
    }
