package com.uvn.ticker.ui.editexteactivity.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.uvn.ticker.R
import com.uvn.ticker.data.TickerParam

class HistoryHistoryAdapter(
    private val listener: HistoryClickListener,
    var tp: MutableList<TickerParam>,
    private val deletedOnSwipe: (TickerParam) -> Unit
) : RecyclerView.Adapter<MessageViewHolder>(), View.OnClickListener,
    HistoryTouchHelper {

    override fun onItemDismiss(position: Int) {
        val tpForDelete = tp[position]
        tp.removeAt(position)
        notifyDataSetChanged()
        deletedOnSwipe.invoke(tpForDelete)
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
        return tp.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val tickerParam = tp[position]
        holder.tvText.setTextColor(tickerParam.textColor)
        holder.tvText.text = tickerParam.text
        holder.textContainer.setCardBackgroundColor(tickerParam.backgroundColor)
        holder.setOnClickListener(this, position)
    }

    override fun onClick(p0: View?) {
        val position = p0?.tag as Int
        listener.onClick(tp[position])
    }

    fun clearAll() {
        tp.clear()
        notifyDataSetChanged()
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    internal val tvText: TextView = view.findViewById(R.id.textView)
    internal val textContainer: CardView = view.findViewById(R.id.textContainer)

    fun setOnClickListener(listener: View.OnClickListener, position: Int) {
        tvText.tag = position
        tvText.setOnClickListener(listener)
    }
}
