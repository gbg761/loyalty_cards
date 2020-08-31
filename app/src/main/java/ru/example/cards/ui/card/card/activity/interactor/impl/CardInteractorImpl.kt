package ru.example.cards.ui.card.card.activity.interactor.impl

import ru.example.cards.model.cards.manager.CardManager
import ru.example.cards.ui.card.card.activity.interactor.CardInteractor

class CardInteractorImpl(private val manager: CardManager) : CardInteractor {

    override fun deleteCard(cardId: Long) = manager.deleteCard(cardId)

    override fun isEmptyUserCardList(): Boolean = manager.getCardListSize() == 0L
}