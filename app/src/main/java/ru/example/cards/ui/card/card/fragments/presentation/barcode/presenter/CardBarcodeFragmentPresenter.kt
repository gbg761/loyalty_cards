package ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter

import ru.example.cards.ui.base.presenter.MvpPresenter
import ru.example.cards.ui.card.card.fragments.CardFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.CardBarcodeFragment

interface CardBarcodeFragmentPresenter :
    CardFragmentPresenter,
    MvpPresenter<CardBarcodeFragment> {

    fun shareCard()

    fun updateBarcode(cardNumber: String?)

    fun pressedReportBugButton()
}