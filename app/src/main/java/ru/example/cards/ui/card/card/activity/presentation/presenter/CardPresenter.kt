package ru.example.cards.ui.card.card.activity.presentation.presenter

import ru.example.cards.ui.base.presenter.MvpPresenter
import ru.example.cards.ui.card.card.activity.presentation.view.CardView

interface CardPresenter : MvpPresenter<CardView> {

    fun onEditCardButtonClick()

    fun onDeleteCardButtonClick()

    fun onBackPressed()
}