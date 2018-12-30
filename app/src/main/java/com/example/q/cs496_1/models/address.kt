package com.example.q.cs496_1.models

class Address (name: String, number:String){
    var name : String = name
    var number : String = number
    var photo : String =""

    constructor(name: String, number: String, photo: String) : this(name,number){
        this.photo = photo
    }
}