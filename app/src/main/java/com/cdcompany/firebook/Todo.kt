package com.cdcompany.firebook

data class Todo(
    var description : String? = "",
    var time : Long = 0,
    var complete : Boolean = false)