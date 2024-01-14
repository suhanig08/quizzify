package com.suhani.quizzify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.suhani.quizzify.databinding.FragmentAddquizBinding
import com.suhani.quizzify.models.Quizmodel

class AddquizFragment : Fragment() {

    private lateinit var binding: FragmentAddquizBinding
    private lateinit var title:EditText
    private lateinit var question:EditText
    private lateinit var option1:EditText
    private lateinit var option2:EditText
    private lateinit var option3:EditText
    private lateinit var option4:EditText
    private lateinit var answer:EditText

    private lateinit var dbRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentAddquizBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun saveaddquizData() {
        val ettitle=title.text.toString()
        val etquestion=question.text.toString()
        val etoption1=option1.text.toString()
        val etoption2=option2.text.toString()
        val etoption3=option3.text.toString()
        val etoption4=option4.text.toString()
        val etanswer=answer.text.toString()

        if(ettitle.isEmpty()){
            title.error="Please enter the title"
        }
        else if(etquestion.isEmpty()){
            question.error="Please enter the question"
        }
        else if(etoption1.isEmpty()){
            option1.error="Please enter option 1"
        }
        else if(etoption2.isEmpty()){
            option2.error="Please enter option 2"
        }
        else if(etoption3.isEmpty()){
            option3.error="Please enter option 3"
        }
        else if(etoption4.isEmpty()){
            option4.error="Please enter option 4"
        }
        else if(etanswer.isEmpty()){
            answer.error="Please enter the answer"
        }
        else {
           // dbRef= Firebase.database.reference
            dbRef=FirebaseDatabase.getInstance().reference
           // val dbRefn = dbRef.database.getReference("Question")
            val quizId = dbRef.push().key!!
            val quiz =
                Quizmodel(
                    quizId,
                    ettitle,
                    etquestion,
                    etoption1,
                    etoption2,
                    etoption3,
                    etoption4,
                    etanswer
                )
            Log.e("runCheck","load run")
            Log.d("runCheck",dbRef.toString())
            dbRef.child("all").setValue(quiz)
                .addOnCompleteListener {
                    Log.e("runCheck","load")
                    Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                    title.text.clear()
                    question.text.clear()
                    option1.text.clear()
                    option2.text.clear()
                    option3.text.clear()
                    option4.text.clear()
                    answer.text.clear()
               // return@onSuccessTask 0
                }.addOnFailureListener { err ->
                    Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title=binding.titlebox
        question=binding.questionbox
        option1=binding.option1
        option2=binding.option2
        option3=binding.option3
        option4=binding.option4
        answer=binding.answer

      //  dbRef=FirebaseDatabase.getInstance().getReference("addquiz")
        binding.submitQuiz.setOnClickListener {
            saveaddquizData()
        }

    }
    }
