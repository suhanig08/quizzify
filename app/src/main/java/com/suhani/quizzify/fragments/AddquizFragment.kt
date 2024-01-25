package com.suhani.quizzify.fragments

import android.annotation.SuppressLint
import android.content.Intent
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
import com.suhani.quizzify.activities.nextactivity
import com.suhani.quizzify.databinding.FragmentAddquizBinding
import com.suhani.quizzify.models.Quizmodel

class AddquizFragment : Fragment() {

    private lateinit var binding: FragmentAddquizBinding
    private lateinit var title:EditText
    private lateinit var amt:EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var time:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentAddquizBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title=binding.titlebox
        amt=binding.amtQ
        time=binding.timeQuiz

        dbRef = FirebaseDatabase.getInstance().getReference("quizzes")
            binding.nextbtn.setOnClickListener {
                if (title.text.toString().isEmpty()) {
                    title.error = "Please enter the title"
                }else if (amt.text.toString().isEmpty()) {
                    amt.error = "Please enter the number of questions you would like to add"
                }else if (time.text.toString().isEmpty()) {
                    amt.error = "Please enter the time "
                } else {
                    val intent = Intent(context, nextactivity::class.java)
                    intent.putExtra("title",title.text.toString())
                    intent.putExtra("amt",amt.text.toString())
                    intent.putExtra("index",1)
                    intent.putExtra("time",time.text.toString())
                    startActivity(intent)
                }
            }


    }
    }
