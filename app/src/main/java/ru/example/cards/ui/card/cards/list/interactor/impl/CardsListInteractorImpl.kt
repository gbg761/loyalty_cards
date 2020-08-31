package ru.example.cards.ui.card.cards.list.interactor.impl

import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.manager.CardManager
import ru.example.cards.ui.card.cards.list.interactor.CardsListInteractor

class CardsListInteractorImpl(private val cardManager: CardManager) : CardsListInteractor {

    override fun getCardList(): List<Card> = cardManager.getCardList()
}