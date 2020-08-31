package ru.example.cards.ui.card.add.presentation.presenter

import ru.example.cards.ui.base.presenter.MvpPresenter
import ru.example.cards.ui.card.add.presentation.view.AddCardView

interface AddCardPresenter :
    MvpPresenter<AddCardView> {

    // нажатие на карту из встроенных
    fun onCardClick(shopName: String, imageName: String)

    // добавить новую карту, отсутствующую в предложенных
    fun onAddNewCardButtonClick()
}