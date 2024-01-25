package com.suhani.quizzify.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.suhani.quizzify.R
import com.suhani.quizzify.activities.startQuiz
import com.suhani.quizzify.models.Quiz
import com.suhani.quizzify.models.Quizmodel
import com.suhani.quizzify.utils.colorPicker
import com.suhani.quizzify.utils.iconPicker

class QuizAdapter(val context: Context?,val titles:List<String>):RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textviewGenre: TextView = itemView.findViewById(R.id.quizGenre)
        var iconView: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer: CardView = itemView.findViewById(R.id.cardContainer)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.quiz_genres,parent,false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {

        holder.textviewGenre.text=titles[position]
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(colorPicker.getColor()))
        holder.iconView.setImageResource(iconPicker.getIcons())
        holder.itemView.setOnClickListener {
            val intent= Intent(context,startQuiz::class.java)
            intent.putExtra("GENRE",titles[position])
            context?.startActivity(intent)
        }

    }
}