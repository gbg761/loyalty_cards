package ru.example.cards.ui.card.card.activity.presentation.view

import android.content.Intent
import ru.example.cards.ui.base.view.MvpView

interface CardView : MvpView {

    fun startEditCardActivity()

    fun startCardsListActivity()

    fun startAnimatedCardsListActivity()

    fun getIntent(): Intent

    fun getCardId(): Long?

    fun updateWidget()
}