package ru.example.cards.model.cards.storage.modules

import ru.example.cards.model.cards.OnSaveCardListener
import ru.example.cards.model.cards.models.Card

interface StorageModule {

    fun addNewCard(card: Card): Boolean

    // перегруженная версия, используется экраном создания карты CreateCardActivityKt
    // т.к. после добавления карты, необходимо получить ее id.
    // В метод OnSaveCardListener.onSaveSuccess(cardId: Long) будем передавать id добавленной карты
    fun addNewCard(card: Card, onSaveCardListener: OnSaveCardListener)

    fun updateCard(card: Card): Boolean

    fun getCardList(): List<Card>

    fun getCardListSize(): Long

    fun getCard(id: Long): Card?

    fun searchCardsByTextQuery(query: String): List<Card>

    fun deleteCard(id: Long)

    fun deleteAllCard()

    fun disconnect()

    fun updateCardAsync(card: Card)
}