package com.example.nakshatratak.recyclerviews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nakshatratak.ChatActivity
import com.example.nakshatratak.MainActivity
import com.example.nakshatratak.OrderActivity
import com.example.nakshatratak.Profile
import com.example.nakshatratak.R
import com.google.android.material.button.MaterialButton
import com.thecode.aestheticdialogs.*

import de.hdodenhof.circleimageview.CircleImageView

class ChatRVadapter(private val context: Context?, private val dataList: ArrayList<Int>) :
    RecyclerView.Adapter<ChatRVadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chatfragmentlayout, parent, false)
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
        private val imageView: CircleImageView = itemView.findViewById(R.id.profileimage)

        private val button:MaterialButton = itemView.findViewById(R.id.button2)

        fun bind(data: Int) {
            imageView.setImageResource(data)

button.setOnClickListener {


    AestheticDialog.Builder(context as Activity, DialogStyle.FLAT, DialogType.SUCCESS)
        .setTitle("Success")
        .setMessage("Check your orders for approval from Astrologer")
        .setCancelable(false)
        .setDarkMode(false)
        .setGravity(Gravity.CENTER)
        .setAnimation(DialogAnimation.CARD)
        .setOnClickListener(object : OnDialogClickListener {
            override fun onClick(dialog: AestheticDialog.Builder) {
                dialog.dismiss()
                //actions...
            }
        })
        .show()



}
            imageView.setOnClickListener {

                context?.startActivity(Intent(context,Profile::class.java))

            }



        }
    }

//
}
