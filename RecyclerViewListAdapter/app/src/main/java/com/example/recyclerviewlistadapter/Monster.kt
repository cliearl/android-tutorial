package com.example.recyclerviewlistadapter

data class Monster(
    val name: String,
    val race: Race,
    val level: Int,
    val stats: List<Int>,
    val encount: Boolean,
)

enum class Race {
    Zombie, Human, Goblin, Dragon,
}