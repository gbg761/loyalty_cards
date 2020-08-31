package ru.example.cards.ui.card.cards.list.interactor

import ru.example.cards.model.cards.models.Card

interface CardsListInteractor {

    fun getCardList(): List<Card>
}