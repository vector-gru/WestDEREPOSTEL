package com.example.westderepostel

interface UpdateAndDelete{
    fun modifyItem(itemUID :String ,isDone :Boolean)
    fun onItemDelete(itemUID: String)
}