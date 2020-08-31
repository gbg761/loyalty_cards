package ru.example.cards.ui.card.add.presentation.presenter.factory

import android.content.Context
import ru.example.cards.App
import ru.example.cards.ui.card.add.interactor.AddCardInteractor
import ru.example.cards.ui.card.add.interactor.impl.AddCardInteractorImpl
import ru.example.cards.ui.card.add.presentation.presenter.impl.AddCardPresenterImpl

class AddCardPresenterFactory {

    companion object {

        fun createPresenter(context: Context): AddCardPresenterImpl {

            val interactor: AddCardInteractor = AddCardInteractorImpl(App.getCardManager(context))
            return AddCardPresenterImpl(interactor)
        }
    }
}