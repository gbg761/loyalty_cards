package ru.example.cards.model.cards.models

data class Card(
    val id: Long = 0,
    var number: String,
    var shopName: String,
    var description: String?,
    var imageName: String?,
    var imageColor: Int? = null,
    var frontSidePath: String? = null,
    var backSidePath: String? = null,
    var lastUsedDate: Long = 0
)