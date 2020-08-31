package ru.example.cards.ui.card.card.activity.presentation.presenter.impl

import ru.example.cards.constants.Constants
import ru.example.cards.ui.base.presenter.BasePresenter
import ru.example.cards.ui.card.card.activity.interactor.CardInteractor
import ru.example.cards.ui.card.card.activity.presentation.presenter.CardPresenter
import ru.example.cards.ui.card.card.activity.presentation.view.CardView

class CardPresenterImpl(private val interactor: CardInteractor) :
    BasePresenter<CardView>(),
    CardPresenter {

    override fun viewIsReady() {
    }

    override fun onBackPressed() {

        // 1.   Если юзер перешел на экран "Карта" с экрана добавления новой карты,
        //      то при нажатии кнопки "назад" необходимо пересоздать(перерисовать)
        //      экран с со списком добавленных карт
        //
        // 2.   Если юзер перешел на экран "Карта" с экрана уже добавленных в хранилище карт
        //      нет необходимости пересоздавать заново активити с этим списком, просто закроем
        //      экран "Карты"
        val args = view?.getIntent()?.extras

        if (args != null) {

            when (args.getInt(Constants.PARENT_ACTIVITY_EXTRA)) {

                Constants.CREATE_NEW_CARD ->
                    view?.startCardsListActivity()

                Constants.OPEN_CARD ->
                    view?.close()
            }

        } else {
            startActivity()
        }
    }

    override fun onEditCardButtonClick() {
        view?.startEditCardActivity()
    }

    override fun onDeleteCardButtonClick() {

        val cardId = view?.getCardId()
        cardId?.let {
            interactor.deleteCard(cardId)
            view?.updateWidget()
        }

        startActivity()
    }

    private fun startActivity() {
        // проверяем есть ли карты в хранилище
        // если в хранилище не было карт, откываем activity c анимированным списком карт
        if (interactor.isEmptyUserCardList()) {
            view?.startAnimatedCardsListActivity()
            view?.close()
        } else {
            // если в хранилище были карты, открываем activity со списком карт
            view?.startCardsListActivity()
            view?.close()
        }
    }
}