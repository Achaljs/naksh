package com.example.nakshatratak.recyclerviews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nakshatratak.MainActivity
import com.example.nakshatratak.Profile
import com.example.nakshatratak.R
import de.hdodenhof.circleimageview.CircleImageView

class AstrologerRVadapter(private val context: Context?, private val dataList: List<DataModel>) :
    RecyclerView.Adapter<AstrologerRVadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.astrologerrecyclerview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: CircleImageView = itemView.findViewById(R.id.profile_image)
        private val textView: TextView = itemView.findViewById(R.id.name)
        private val rate: TextView = itemView.findViewById(R.id.rate)

        fun bind(data: DataModel) {
            imageView.setImageResource(data.image)
            textView.text = data.name
            rate.text= data.rate

            imageView.setOnClickListener {

                context?.startActivity(Intent(context,Profile::class.java))

            }


        }
    }

//
}
