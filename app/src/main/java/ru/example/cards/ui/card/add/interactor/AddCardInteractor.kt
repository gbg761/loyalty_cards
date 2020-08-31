package ru.example.cards.ui.card.add.interactor

import ru.example.cards.model.cards.models.InputCardWithType

interface AddCardInteractor {

    fun getCardList(): List<InputCardWithType>
}