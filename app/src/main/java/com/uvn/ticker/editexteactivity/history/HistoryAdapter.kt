package com.uvn.ticker.editexteactivity.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uvn.ticker.R

class HistoryHistoryAdapter(
    private val listener: HistoryClickListener,
    var messages: MutableList<String>,
    private val deletedOnSwipe: () -> Unit
) : RecyclerView.Adapter<MessageViewHolder>(), View.OnClickListener,
    HistoryTouchHelper {

    override fun onItemDismiss(position: Int) {
        messages.removeAt(position)
        notifyDataSetChanged()
        deletedOnSwipe.invoke()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.meassage,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.tvText.text = messages[position]
        holder.setOnClickListener(this, position)
    }

    override fun onClick(p0: View?) {
        val position = p0?.tag as Int
        listener.onClick(messages[position])
    }

    fun clearAll() {
        messages.clear()
        notifyDataSetChanged()
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    internal val tvText: TextView = view.findViewById(R.id.textView)

    fun setOnClickListener(listener: View.OnClickListener, position: Int) {
        tvText.tag = position
        tvText.setOnClickListener(listener)
    }
}
