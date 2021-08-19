package com.example.westderepostel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.westderepostel.models.OperatorInfo
import com.google.firebase.firestore.auth.User

class MyOAdapter(private val operatorList : ArrayList<OperatorInfo>) : RecyclerView.Adapter<MyOAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.operator_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = operatorList[position]

        holder.operatorName.text = currentitem.operatorName
        holder.email.text = currentitem.email
        holder.phone.text = currentitem.phone
        holder.location.text = currentitem.location
        holder.activity.text = currentitem.activity

    }

    override fun getItemCount(): Int {

        return operatorList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val operatorName : TextView = itemView.findViewById(R.id.tvOperatorName)
        val email : TextView = itemView.findViewById(R.id.tvEmail)
        val phone : TextView = itemView.findViewById(R.id.tvPhone)
        val location : TextView = itemView.findViewById(R.id.tvLocation)
        val activity : TextView = itemView.findViewById(R.id.tvActivity)

    }

}