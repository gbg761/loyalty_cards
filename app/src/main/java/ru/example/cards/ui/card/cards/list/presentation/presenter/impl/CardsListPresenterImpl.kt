package ru.example.cards.ui.card.cards.list.presentation.presenter.impl

import android.content.Context
import android.content.Intent
import ru.example.cards.ui.base.presenter.BasePresenter
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.cards.list.interactor.CardsListInteractor
import ru.example.cards.ui.card.cards.list.presentation.presenter.CardsListPresenter
import ru.example.cards.ui.card.cards.list.presentation.view.CardsListView

class CardsListPresenterImpl(
    private val interactor: CardsListInteractor
) : BasePresenter<CardsListView>(),
    CardsListPresenter {

    // view готово для отображения списка сохраненных карт
    override fun viewIsReady() = showCards()

    private fun showCards() {
        view?.showCards(interactor.getCardList())
    }

    override fun open(operationID: Int, context: Context, cls: Class<*>, cardID: Long) {

        when (operationID) {

            Constants.OPEN_CARD -> {
                val intent = Intent(context, cls).apply {
                    putExtra(Constants.OPEN_CARD_EXTRA, cardID)
                    putExtra(Constants.PARENT_ACTIVITY_EXTRA, Constants.OPEN_CARD)
                }

                view?.next(intent)
                //view?.close()
            }

            Constants.OPEN_SETTINGS -> {
                val intent = Intent(context, cls)
                view?.next(intent)
            }

            Constants.CREATE_NEW_CARD -> {
                val intent = Intent(context, cls)
                view?.next(intent)
                //view?.close()
            }
        }
    }
}