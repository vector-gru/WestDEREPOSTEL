package com.example.westderepostel.models

class  ChatMessage(val id: String, val text: String, val fromId: String, val timestamp: Long) {
    constructor() : this(id = "", text = "", fromId = "", timestamp = -1)
}