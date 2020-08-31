package ru.example.cards.ui.card.add.presentation.view

import ru.example.cards.ui.base.view.MvpView
import ru.example.cards.model.cards.models.InputCardWithType

interface AddCardView : MvpView {

    fun showCards(cards: List<InputCardWithType>)

    // запустить активити сканирования штрих кода карты,
    // выбрав магазин из вшитого списка карт
    fun startBarcodeScannerActivity(shopName: String, imageName: String)

    // запустить активити сканирования штрих кода карты
    // не выбирая карту из вшитого списка карт
    fun startBarcodeScannerActivity()
}