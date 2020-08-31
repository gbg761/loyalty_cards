package ru.example.cards.model.cards.manager

import android.content.Intent
import android.net.Uri
import ru.example.cards.model.cards.OnSaveCardListener
import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.models.InputCard

interface CardManager {

    fun getCards(): MutableList<Card>

    fun updateCardsForWidget()

    fun save(card: Card): Boolean

    fun save(card: Card, onSaveCardListener: OnSaveCardListener)

    fun getCardList(): List<Card>

    fun getCardListSize(): Long

    fun getCard(id: Long): Card?

    fun searchCardsByTextQuery(query: String): List<Card>

    fun deleteCard(id: Long)

    fun deleteAllCard()

    fun disconnect()

    fun updateCardAsync(card: Card)

    //работа с парсером
    fun read()

    fun getInputCardList(): List<InputCard>

    fun checkInternetConnection(): Boolean

    fun shareCard(card: Card?): Intent?

    fun getCardFromShareableFile(sharedCardUri: Uri): Card?

    fun isCardPhotoExist(path: String): Boolean
}