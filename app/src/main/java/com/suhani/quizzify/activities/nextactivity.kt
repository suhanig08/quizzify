package com.suhani.quizzify.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.suhani.quizzify.databinding.ActivityNextactivityBinding
import com.suhani.quizzify.models.Quizmodel

class nextactivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextactivityBinding
    private lateinit var question: EditText
    private lateinit var option1: EditText
    private lateinit var option2: EditText
    private lateinit var option3: EditText
    private lateinit var option4: EditText
    private lateinit var answer: EditText
    private lateinit var x: String
    private  var amtQ=0
    private var time=0
    private lateinit var dbRef: DatabaseReference
    private var index=0
    private var f=0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        x = intent.getStringExtra("title").toString()
        amtQ= intent.getStringExtra("amt")!!.toInt()
        index= intent.getIntExtra("index",0)
        time=intent.getStringExtra("time")!!.toInt()
        Log.e("runnn",time.toString())
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes")
        bindviews()
        initialzeit()
    }

    private fun initialzeit() {
            question = binding.descriptionbox
            option1 = binding.opt1
            option2 = binding.opt2
            option3 = binding.opt3
            option4 = binding.opt4
            answer = binding.correctAns
            binding.addquizQno.text= "${index}."
            binding.nextbtnadd.setOnClickListener {
                saveaddquizdata()
                if(f==1) {
                    resetactivity()
                }
            }
            binding.donebtn.setOnClickListener {
                saveaddquizdata()
                if(f==1)
                    backtoaddquiz()
            }
    }

    private fun backtoaddquiz() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Quiz added successfully", Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun bindviews() {
        binding.donebtn.visibility = View.GONE
        binding.nextbtnadd.visibility=View.GONE
        if (index == amtQ) {
            binding.nextbtnadd.visibility = View.GONE
            binding.donebtn.visibility = View.VISIBLE
        }else{
            binding.nextbtnadd.visibility=View.VISIBLE
            binding.donebtn.visibility=View.GONE
        }
    }

    private fun saveaddquizdata() {
        val etamt=amtQ
        val ettitle = x
        val etquestion = question.text.toString()
        val etoption1 = option1.text.toString()
        val etoption2 = option2.text.toString()
        val etoption3 = option3.text.toString()
        val etoption4 = option4.text.toString()
        val etanswer = answer.text.toString()
        if (etquestion.isEmpty()) {
            question.error = "Please enter the question"
        } else if (etoption1.isEmpty()) {
            option1.error = "Please enter option 1"
        } else if (etoption2.isEmpty()) {
            option2.error = "Please enter option 2"
        } else if (etoption3.isEmpty()) {
            option3.error = "Please enter option 3"
        } else if (etoption4.isEmpty()) {
            option4.error = "Please enter option 4"
        } else if (etanswer.isEmpty()) {
            answer.error = "Please enter the answer"
        } else {
            f=1
            val quiz =
                Quizmodel(
                    etamt,
                    time,
                    ettitle,
                    etquestion,
                    etoption1,
                    etoption2,
                    etoption3,
                    etoption4,
                    etanswer
                )
            dbRef.child(x).push().setValue(quiz)
                .addOnCompleteListener {
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun resetactivity() {
        val intent = Intent(this, nextactivity::class.java)
        intent.putExtra("index",index+1)
        intent.putExtra("amt",amtQ.toString())
        intent.putExtra("title",x)
        intent.putExtra("time",time.toString())
        startActivity(intent)
    }

}