package com.demirli.a57battleshipgameengine

class Ship(
    var name: String,
    var squares: Int,
    var shipCoordinates: ArrayList<Pair<Int, Int>>? = null,
    var hitPoint: Int? = null,
    var isSunk: Boolean = false

) {

    companion object{
        fun shipIntersectingControl(shipList: List<Ship>): Boolean{

            val shipCorrdinatesListForComparison = ArrayList<Pair<Int, Int>>()
            val shipCorrdinatesSetForComparison = HashSet<Pair<Int, Int>>()

            for(i in shipList){
                for(j in 0 until i.shipCoordinates!!.size){
                    shipCorrdinatesListForComparison.add(i.shipCoordinates!![j])
                    shipCorrdinatesSetForComparison.add(i.shipCoordinates!![j])
                }
            }

            if(shipCorrdinatesSetForComparison.size < shipCorrdinatesListForComparison.size){
                return true
            }else{
                return false
            }
        }
    }

    init {
        hitPoint = squares
        generateShip()
    }

    private fun generateShip(){

        shipCoordinates = ArrayList<Pair<Int, Int>>()

        val direction = (0..1).random() //0-horizontal, 1-vertical

        var xStartingPoint: Int?
        var yStartingPoint: Int?
        var x2: Int?
        var y2: Int?
        var x3: Int?
        var y3: Int?
        var x4: Int?
        var y4: Int?

        if(direction == 0){ //Horizontal
            if(squares == 2){
                xStartingPoint = (0..6).random()
                yStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint,yStartingPoint))

                x2 = xStartingPoint+1
                y2 = yStartingPoint

                shipCoordinates!!.add(Pair(x2,y2))

            }else if(squares == 3){
                xStartingPoint = (0..5).random()
                yStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint,yStartingPoint))

                x2 = xStartingPoint+1
                y2 = yStartingPoint

                shipCoordinates!!.add(Pair(x2,y2))

                x3 = xStartingPoint+2
                y3 = yStartingPoint

                shipCoordinates!!.add(Pair(x3,y3))
            }else if(squares == 4){
                xStartingPoint = (0..4).random()
                yStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint,yStartingPoint))

                x2 = xStartingPoint+1
                y2 = yStartingPoint

                shipCoordinates!!.add(Pair(x2,y2))

                x3 = xStartingPoint+2
                y3 = yStartingPoint

                shipCoordinates!!.add(Pair(x3,y3))

                x4 = xStartingPoint+3
                y4 = yStartingPoint

                shipCoordinates!!.add(Pair(x4,y4))
            }
        }else if(direction == 1){//Vertical
            if(squares == 2){
                yStartingPoint = (0..6).random()
                xStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint,yStartingPoint))

                y2 = yStartingPoint+1
                x2 = xStartingPoint

                shipCoordinates!!.add(Pair(x2,y2))

            }else if(squares == 3){
                yStartingPoint = (0..5).random()
                xStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint,yStartingPoint))

                y2 = yStartingPoint+1
                x2 = xStartingPoint

                shipCoordinates!!.add(Pair(x2,y2))

                y3 = yStartingPoint+2
                x3 = xStartingPoint

                shipCoordinates!!.add(Pair(x3,y3))
            }else if(squares == 4) {
                yStartingPoint = (0..4).random()
                xStartingPoint = (0..7).random()

                shipCoordinates!!.add(Pair(xStartingPoint, yStartingPoint))

                y2 = yStartingPoint + 1
                x2 = xStartingPoint

                shipCoordinates!!.add(Pair(x2, y2))

                y3 = yStartingPoint + 2
                x3 = xStartingPoint

                shipCoordinates!!.add(Pair(x3, y3))

                y4 = yStartingPoint + 3
                x4 = xStartingPoint

                shipCoordinates!!.add(Pair(x4, y4))
            }
        }
    }

    fun getHit(strikedCoordinates: Pair<Int,Int>): Boolean{
        if(shipCoordinates!!.contains(strikedCoordinates)){

            hitPoint = hitPoint!! - 1

            if(hitPoint == 0){
                isSunk = true
            }

            return true
        }
        return false
    }
}