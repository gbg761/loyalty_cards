package ru.example.cards.ui.card.card.activity.presentation.presenter.factory

import android.content.Context
import ru.example.cards.App
import ru.example.cards.ui.card.card.activity.interactor.CardInteractor
import ru.example.cards.ui.card.card.activity.interactor.impl.CardInteractorImpl
import ru.example.cards.ui.card.card.activity.presentation.presenter.impl.CardPresenterImpl

class CardPresenterFactory {

    companion object {

        fun createCardPresenter(context: Context): CardPresenterImpl {

            val interactor: CardInteractor = CardInteractorImpl(App.getCardManager(context))
            return CardPresenterImpl(interactor)
        }
    }
}