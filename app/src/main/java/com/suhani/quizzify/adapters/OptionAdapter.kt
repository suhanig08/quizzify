package com.suhani.quizzify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suhani.quizzify.R
import com.suhani.quizzify.models.Quizmodel

class OptionAdapter(val context: Context,val question: List<Quizmodel>,val pos: Int):
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

        private var options: List<String?> =
            listOf(question[pos].option1,question[pos].option2,question[pos].option3,question[pos].option4)

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var optionview=itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return OptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
      holder.optionview.text=options[position]
        holder.itemView.setOnClickListener{
            question[pos].userans=options[position]
            notifyDataSetChanged()
        }
        if(question[pos].userans==options[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }
}