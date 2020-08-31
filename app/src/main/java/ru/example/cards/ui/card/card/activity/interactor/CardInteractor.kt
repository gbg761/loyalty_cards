package ru.example.cards.ui.card.card.activity.interactor

interface CardInteractor {

    fun deleteCard(cardId: Long)

    fun isEmptyUserCardList(): Boolean
}