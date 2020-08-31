package ru.example.cards.model.cards.storage.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// класс описывает карту, добавленную пользователем в свои карты
open class CardEntity(
    @PrimaryKey
    open var id: Long = 0,
    open var number: String = "",
    open var shopName: String = "",
    open var cardDescription: String? = null,
    open var imageName: String? = null,
    open var imageColor: Int? = null,
    open var cardFrontSidePath: String? = null,
    open var cardBackSidePath: String? = null,
    open var lastUsedDate: Long = 0
) : RealmObject()