package ru.example.cards.ui.card.add.interactor.impl

import ru.example.cards.model.cards.models.InputCardWithType
import ru.example.cards.model.cards.manager.CardManager
import ru.example.cards.model.cards.manager.reader.Reader
import ru.example.cards.ui.card.add.interactor.AddCardInteractor

class AddCardInteractorImpl(private val manager: CardManager) : AddCardInteractor {

    override fun getCardList(): List<InputCardWithType> {
        manager.read()
        return Reader.addAlphabets(manager.getInputCardList())
    }
}