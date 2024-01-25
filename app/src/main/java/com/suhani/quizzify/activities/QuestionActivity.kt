package com.suhani.quizzify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.suhani.quizzify.R
import com.suhani.quizzify.adapters.OptionAdapter
import com.suhani.quizzify.models.Question
import com.suhani.quizzify.models.Quiz
import com.suhani.quizzify.models.Quizmodel
import java.io.Serializable
import kotlin.time.times

class QuestionActivity : AppCompatActivity() {

    var index = 0
    private lateinit var x:String
    private lateinit var dbRef:DatabaseReference
    private var questions = mutableListOf<Quizmodel>()
    private var timer=0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        x=intent.getStringExtra("title").toString()
        findViewById<TextView>(R.id.quizCat).text=x
        timer=intent.getStringExtra("time")!!.toLong()
        setUpFireBase()
        object :CountDownTimer(timer*1000,1000 ){
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.quiztime).setText("Time: "+millisUntilFinished / 1000)
            }

            override fun onFinish() {
                findViewById<TextView>(R.id.quiztime).setText( "Time Up!" )
                over()
            }
        }.start()
        setUpEventListener()
    }

    private fun over() {
        val intent= Intent(this,ResultActivity::class.java)
        intent.putExtra("title",x)
        intent.putExtra("list",questions as Serializable)
        startActivity(intent)
    }

    private fun setUpFireBase() {
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes").child(x)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                questions.clear()
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val quizData=quizSnap.getValue(Quizmodel::class.java)
                        questions.add(quizData!!)
                        bindViews()
                    }
                    findViewById<TextView>(R.id.Qno).text="${index +1}/${questions[index].amt}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setUpEventListener() {
      findViewById<Button>(R.id.btnPrevious).setOnClickListener{
          index--
          bindViews()
          findViewById<TextView>(R.id.Qno).text="${index +1}/${questions[index].amt}"
      }
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            index++
            bindViews()
            findViewById<TextView>(R.id.Qno).text="${index +1}/${questions[index].amt}"
        }
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val intent= Intent(this,ResultActivity::class.java)
            intent.putExtra("title",x)
            intent.putExtra("list",questions as Serializable)
            startActivity(intent)
        }
    }

    private fun bindViews() {
        findViewById<Button>(R.id.btnPrevious).visibility = View.GONE
        findViewById<Button>(R.id.btnNext).visibility = View.GONE
        findViewById<Button>(R.id.btnSubmit).visibility = View.GONE

        if (index == 0) {
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
        } else if (index == questions!!.size-1) {
            findViewById<Button>(R.id.btnSubmit).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
        } else {
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
        }

            findViewById<TextView>(R.id.description).text = questions[index].description
            val optionAdapter = OptionAdapter(this, questions,index)
            findViewById<RecyclerView>(R.id.optionlist).layoutManager = LinearLayoutManager(this)
            findViewById<RecyclerView>(R.id.optionlist).adapter = optionAdapter
            findViewById<RecyclerView>(R.id.optionlist).setHasFixedSize(true)

    }
}