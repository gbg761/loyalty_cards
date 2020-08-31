package ru.example.cards.ui.card.card.fragments.presentation.factory

import android.content.Context
import android.content.Intent
import ru.example.cards.App
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.fragments.CardFragmentPresenter
import ru.example.cards.ui.card.card.fragments.interactor.CardFragmentInteractor
import ru.example.cards.ui.card.card.fragments.interactor.impl.CardFragmentInteractorImpl
import ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.CardAdditionallyFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.impl.CardAdditionallyPresenterImpl
import ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.CardBarcodeFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.impl.CardBarcodeFragmentPresenterImpl

class CardFragmentPresenterFactory {

    companion object {

        fun createAllCardFragmentPresenters(context: Context, intent: Intent): Map<String, CardFragmentPresenter> {

            val uri = intent.data
            val cardId = intent.extras?.get(Constants.OPEN_CARD_EXTRA) as Long?

            val interactor: CardFragmentInteractor =
                CardFragmentInteractorImpl(App.getCardManager(context), cardId, uri)

            val cardBarcodeFragmentPresenter: CardBarcodeFragmentPresenter =
                CardBarcodeFragmentPresenterImpl(context, interactor)

            val cardAdditionallyPresenter: CardAdditionallyFragmentPresenter =
                CardAdditionallyPresenterImpl(context, interactor)

            return mapOf(
                Constants.KEY_PRESENTER_BARCODE to cardBarcodeFragmentPresenter,
                Constants.KEY_PRESENTER_ADDITIONALLY to cardAdditionallyPresenter
            )
        }
    }
}