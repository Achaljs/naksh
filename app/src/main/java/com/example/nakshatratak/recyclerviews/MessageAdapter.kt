package com.example.nakshatratak.recyclerviews

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.nakshatratak.R

class MessageAdapter(private val messages: MutableList<com.example.nakshatratak.Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.messageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageTextView.text = message.content

        val layoutParams = holder.messageTextView.layoutParams as LinearLayout.LayoutParams
        if (message.isSent) {
            layoutParams.gravity = Gravity.END
            holder.messageTextView.setBackgroundResource(R.drawable.messagelayout)
        } else {
            layoutParams.gravity = Gravity.START
            holder.messageTextView.setBackgroundResource(R.drawable.messagelayout_left)
        }
        holder.messageTextView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
