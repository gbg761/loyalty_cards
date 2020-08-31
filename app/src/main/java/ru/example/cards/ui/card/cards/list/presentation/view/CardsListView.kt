package ru.example.cards.ui.card.cards.list.presentation.view

import android.content.Intent
import ru.example.cards.ui.base.view.MvpView
import ru.example.cards.model.cards.models.Card

interface CardsListView: MvpView {

    fun showCards(cards: List<Card>)

    fun next(intent: Intent)
}