package com.cdcompany.firebook

data class Todo(
    var key : String = "",
    var description : String? = "",
    var time : Long = 0,
    var complete : Boolean = false)