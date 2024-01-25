package com.suhani.quizzify.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.suhani.quizzify.R
import com.suhani.quizzify.models.Question
import com.suhani.quizzify.models.Quiz
import com.suhani.quizzify.models.Quizmodel
import java.io.Serializable

class ResultActivity : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference
    lateinit var quiz:Quizmodel
    private lateinit var x:String
    private var questions =mutableListOf<Quizmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        x=intent.getStringExtra("title").toString()
        questions=intent.getSerializableExtra("list") as MutableList<Quizmodel>
        Log.d("listttt",questions.toString())
        setUpFireBase()
        setAnswerView()
    }

    private fun setUpFireBase() {
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes").child(x)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val quizData=quizSnap.getValue(Quizmodel::class.java)
                        questions.add(quizData!!)
                    }
                    calculateScore()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setAnswerView() {
       val builder=StringBuilder("")
        for(entry in questions){
            val question: String? =entry.description
            builder.append("<font color='#18206F'><b>Questions: ${entry.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${entry.answer}</font><br/><br/>")
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
            findViewById<TextView>(R.id.txtAnswer).text= Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT);
        }else{
            findViewById<TextView>(R.id.txtAnswer).text=Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score=0
        Log.d("scorel",questions.toString())
        for (entry in questions){
            Log.d("scorel",entry.toString())
            if(entry.answer==entry.userans) {
                score += 10
            }
        }

        findViewById<TextView>(R.id.txtScore).text="Your Score: $score"
    }
}