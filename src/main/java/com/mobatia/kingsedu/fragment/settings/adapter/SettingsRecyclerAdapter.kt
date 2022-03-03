package com.mobatia.kingsedu.fragment.settings.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.fragment.home.mContext
import com.mobatia.kingsedu.fragment.termdates.adapter.TermDatesRecyclerAdapter
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListDetailModel
import com.mobatia.kingsedu.manager.PreferenceData

var sharedprefs: PreferenceData= PreferenceData()
internal class SettingsRecyclerAdapter (private var mContext:Context,private var settingsArrayList: List<String>) :
    RecyclerView.Adapter<SettingsRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.listTxtTitle)
        var txtUser: TextView = view.findViewById(R.id.txtUser)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_settings_new, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = settingsArrayList[position]
        holder.title.text = movie.toString()

        if (sharedprefs.getUserCode(mContext).equals(""))
        {

            if (position==4)
            {

                holder.txtUser.visibility=View.VISIBLE
                holder.txtUser.text = "(Guest)"
            }
            else{

                holder.txtUser.visibility=View.GONE
            }
        }
        else
        {
                if (position==5)
                {

                    holder.txtUser.visibility=View.VISIBLE
                    holder.txtUser.text = "("+sharedprefs.getUserEmail(mContext)+")"
                }
                else{

                    holder.txtUser.visibility=View.GONE
                }

        }


    }
    override fun getItemCount(): Int {

        return settingsArrayList.size

    }
}