package ru.example.cards.ui.card.cards.list.presentation.presenter

import android.content.Context
import ru.example.cards.ui.base.presenter.MvpPresenter
import ru.example.cards.ui.card.cards.list.presentation.view.CardsListView

interface CardsListPresenter :
    MvpPresenter<CardsListView> {

    // открыть экран с выбранной картой
    fun open(operationID: Int, context: Context, cls: Class<*>, cardID: Long = -1)

}