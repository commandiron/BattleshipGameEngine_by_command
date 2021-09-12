package com.demirli.a57battleshipgameengine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.function.Predicate
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private var shipList: List<Ship>? = null

    private lateinit var adapter: ShipAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUi()
    }

    private fun setUi(){

        preparationForTheGame()
        lockLayoutAndSetUiElements()

        start_btn.setOnClickListener {
            unLockLayoutAndSetUiElements()
            startSinglePlayerGame()
        }

        restart_btn.setOnClickListener {
            preparationForTheGame()
            lockLayoutAndSetUiElements()
        }

        add_manuel_btn.setOnClickListener {
            //Burada random yerine manuel koordinat ile oynatacağız. Bunun için buna tıklandığında,
            //bir sayfa açılmalı ve burada, shiplerin koordinatları set edilmeli. Okay diyip tekrar
            //geriye dönüldüğünde Start button üzerinde START (MANUEL COORDINATES) yazmalı ve oyun
            //bu şekilde devam etmeli, restarta tıklandığında bir anlamı olmayacağından manuel kor-
            //dinatlarla değil oyunun en baştaki haline geri dönmeli. Yapacaklarım bunlar.
        }
    }

    private fun preparationForTheGame(){

        val layout = gridLayout
        for(i in 0 until layout.childCount){

            val child = layout.getChildAt(i)
            val radioButton = findViewById<RadioButton>(child.id)

            radioButton.setBackgroundColor(Color.WHITE)
            radioButton.isChecked = false
            radioButton.isClickable = true
        }

        createShips()

    }

    private fun createShips() {
        //Eğer istenirse manuel olarak ship eklenip oyuna dahil edilebilir. Yada ui'a ship ekleme özelliği getirilebilir.
        val destroyer = Ship(name = "Destroyer", squares = 2)
        val cruiser = Ship(name = "Cruiser", squares = 3)
        val battleship = Ship(name = "Battleship", squares = 4)
        shipList = listOf(destroyer!!, cruiser!!, battleship!!)
    }

    private fun lockLayoutAndSetUiElements(){

        val layout = gridLayout
        for(i in 0 until layout.childCount){
            val child = layout.getChildAt(i)
            child.isEnabled = false
        }

        start_btn.visibility = View.VISIBLE
        restart_btn.visibility = View.INVISIBLE
        add_manuel_btn.visibility = View.VISIBLE
        information_tv.setText("Click start button for play.")

        setShipListAdapter(shipList!!)
    }

    private fun setShipListAdapter(shipList: List<Ship>){
        shipList_recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShipAdapter(shipList)
        shipList_recyclerView.adapter = adapter
    }

    private fun unLockLayoutAndSetUiElements(){

        val layout = gridLayout
        for(i in 0 until layout.childCount){

            val child = layout.getChildAt(i)
            child.isEnabled = true
        }

        start_btn.visibility = View.GONE
        restart_btn.visibility = View.VISIBLE
        add_manuel_btn.visibility = View.GONE
        information_tv.setText("Everything is ready, ships have been placed. Hit wherever you want!")
    }

    private fun startSinglePlayerGame(){

        createShips()

        //For prevent shipIntersecting
        if(Ship.shipIntersectingControl(shipList!!)){
            startSinglePlayerGame()
        }

        setShipListAdapter(shipList!!)

        radioButtonListener()
    }

    private fun radioButtonListener(){

        val layout = gridLayout
        for(i in 0 until layout.childCount){

            val child = layout.getChildAt(i)
            child.setOnClickListener {

                val clickedResName = resources.getResourceName(it.id)
                val result = clickedResName.filter { it.isDigit() }
                val strikedCoordinates = Pair(Character.getNumericValue(result[2]), Character.getNumericValue(result[3]))

                val radioButton = findViewById<RadioButton>(it.id)

                hitOrMissAndUpdateUi(strikedCoordinates, radioButton)
            }
        }
    }

    private fun hitOrMissAndUpdateUi(strikedCoordinates: Pair<Int, Int>, radioButton: RadioButton) {

        var hitFlag = false

        for(i in shipList!!){

            if(i.getHit(strikedCoordinates)){

                radioButton.setBackgroundColor(Color.GREEN)
                radioButton.isChecked = true
                radioButton.isClickable = false
                information_tv.setText("Hit!")

                if(i.isSunk == true){
                    information_tv.setText("Hit! Congratulations!!! ${i.name} sunk.")
                    adapter.notifyDataSetChanged()
                    checkAllShipsSunk()
                }

                hitFlag = true

            }else{

                if(hitFlag == false){

                    radioButton.setBackgroundColor(Color.RED)
                    radioButton.isChecked = false
                    radioButton.isClickable = false
                    information_tv.setText("Miss...")
                }
            }
        }
    }

    private fun checkAllShipsSunk(){
        if(shipList!!.asSequence().all {it.isSunk == true}){
            information_tv.setText("Congratulations!!! All ships sunk! GG. ")
            val layout = gridLayout
            for(i in 0 until layout.childCount){
                val child = layout.getChildAt(i)
                child.isEnabled = false
            }
        }
    }
}
