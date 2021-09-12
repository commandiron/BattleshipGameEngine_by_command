package com.demirli.a57battleshipgameengine

import android.graphics.Paint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShipAdapter(var shipList: List<Ship>): RecyclerView.Adapter<ShipAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ship_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = shipList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shipNameSquares.setText(shipList[position].name + " - " + shipList[position].squares.toString() + " Squares")

        if(shipList[position].isSunk == true){
            holder.shipNameSquares.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val shipNameSquares = view.findViewById<TextView>(R.id.ship_name_squares_tv)
    }
}