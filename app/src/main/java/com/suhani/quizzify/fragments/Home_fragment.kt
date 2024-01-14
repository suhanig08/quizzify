package com.suhani.quizzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.suhani.quizzify.R
import com.suhani.quizzify.adapters.QuizAdapter
import com.suhani.quizzify.models.Quiz


class home_fragment : Fragment() {

    private lateinit var adapter: QuizAdapter
    private var quizlist= mutableListOf<Quiz>()
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFireStore()
        adapter= QuizAdapter(context,quizlist)
        val recyclerView: RecyclerView =view.findViewById(R.id.quizRecyclerView)
        recyclerView.layoutManager= GridLayoutManager(context, 2)
        recyclerView.adapter=adapter

    }

    private fun setUpFireStore() {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference =firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if(value==null||error!=null){
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizlist.clear()
            quizlist.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }
}