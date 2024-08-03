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
import com.example.nakshatratak.VedioCallActivity
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView

class CallRVadapter(private val context: Context?, private val dataList: ArrayList<Int>) :
    RecyclerView.Adapter<CallRVadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.callfragmentlayout, parent, false)
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
        private val imageView: CircleImageView = itemView.findViewById(R.id.callimage)

private val vediocallButton:MaterialButton=itemView.findViewById(R.id.buttonVideocall)

        fun bind(data: Int) {
            imageView.setImageResource(data)


            imageView.setOnClickListener {

                context?.startActivity(Intent(context,Profile::class.java))

            }
             vediocallButton.setOnClickListener {
                 context?.startActivity(Intent(context,VedioCallActivity::class.java))
             }


        }
    }

//
}
