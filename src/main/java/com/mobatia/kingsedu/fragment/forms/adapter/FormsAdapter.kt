package com.mobatia.kingsedu.fragment.forms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.fragment.forms.model.FormsResponseArrayDetail
import com.mobatia.kingsedu.fragment.messages.adapter.MessageListRecyclerAdapter
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

internal class FormsAdapter (private var messageArrayList: List<FormsResponseArrayDetail>) :
    RecyclerView.Adapter<FormsAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var Img: ImageView = view.findViewById(R.id.Img)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_message_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = messageArrayList[position]
        holder.title.text = movie.title
        holder.Img.visibility=View.GONE
    }
    override fun getItemCount(): Int {

        return messageArrayList.size

    }
}