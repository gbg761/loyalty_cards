package ru.example.cards.ui.card.cards.list.presentation.presenter.factory

import android.content.Context
import ru.example.cards.App
import ru.example.cards.ui.card.cards.list.interactor.CardsListInteractor
import ru.example.cards.ui.card.cards.list.interactor.impl.CardsListInteractorImpl
import ru.example.cards.ui.card.cards.list.presentation.presenter.impl.CardsListPresenterImpl

class CardsListPresenterFactory {

    companion object {

        fun createPresenter(context: Context): CardsListPresenterImpl {

            val interactor: CardsListInteractor = CardsListInteractorImpl(App.getCardManager(context))
            return CardsListPresenterImpl(interactor)
        }
    }
}