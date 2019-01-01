package com.example.q.cs496_1.models

import java.io.Serializable

class Food : Serializable {
    var time: String? = null
    var food: String? = null

    constructor(time: String, food: String) {
        this.time= time
        this.food = food
    }
}