package com.mobatia.kingsedu.activity.home.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.activity.home.FirstScreenNewData
import com.mobatia.kingsedu.fragment.home.mContext
import com.mobatia.kingsedu.fragment.home.model.datacollection.KinDetailApiModel

 class FamilyContactRecyclerAdapter (private var familyContactArrayList: ArrayList<KinDetailApiModel>) :
    RecyclerView.Adapter<FamilyContactRecyclerAdapter.MyViewHolder>() {
     inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameOwnDetailTxt: TextView = view.findViewById(R.id.nameOwnDetailTxt)
         var confirm_text:TextView = view.findViewById(R.id.confirm_text)
        var contactTypeOwnDetailTxt: TextView = view.findViewById(R.id.contactTypeOwnDetailTxt)
        var confirmBtn: ImageView = view.findViewById(R.id.confirmBtn)
        var ownDetailViewRelative: RelativeLayout = view.findViewById(R.id.ownDetailViewRelative)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_family_contact, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameOwnDetailTxt.text = familyContactArrayList.get(position).name
        holder.contactTypeOwnDetailTxt.text = familyContactArrayList.get(position).relationship

        if (familyContactArrayList.get(position).isConfirmed)
        {
            Log.e("ARRAYSIZE","Adapter")
            holder.confirmBtn.visibility=View.GONE
            holder.confirm_text.visibility=View.GONE
            holder.ownDetailViewRelative.setBackgroundResource(R.drawable.rect_background_grey)

        }
        else{
            holder.confirmBtn.visibility=View.VISIBLE
            holder.confirm_text.visibility=View.VISIBLE
            holder.ownDetailViewRelative.setBackgroundResource(R.drawable.rect_data_collection_red)
        }


    }
    override fun getItemCount(): Int {

        Log.e("ARRAYSIZE",familyContactArrayList.size.toString())
        return familyContactArrayList.size

    }
}