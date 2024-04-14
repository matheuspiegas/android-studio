package com.example.convidados

data class GuestModel(val id: Int, var name: String) {
    override fun toString(): String = "$id - $name"
}
