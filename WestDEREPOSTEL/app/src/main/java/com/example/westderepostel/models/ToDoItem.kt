package com.example.westderepostel.models

data class ToDoItem (var id:String="" , var level:String="", var status:Boolean=false){

    public fun statusChanged(){
        status=!status
    }
}
