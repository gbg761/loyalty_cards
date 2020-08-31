package ru.example.cards.ui.card.add.presentation.presenter.impl

import ru.example.cards.ui.base.presenter.BasePresenter
import ru.example.cards.ui.card.add.interactor.AddCardInteractor
import ru.example.cards.ui.card.add.presentation.presenter.AddCardPresenter
import ru.example.cards.ui.card.add.presentation.view.AddCardView

class AddCardPresenterImpl(
    private val interactor: AddCardInteractor
) : BasePresenter<AddCardView>(),
    AddCardPresenter {

    override fun viewIsReady() {
        view?.showCards(interactor.getCardList())
    }

    // нажатие на карту из встроенных
    override fun onCardClick(shopName: String, imageName: String) {

        view?.startBarcodeScannerActivity(shopName, imageName)
        //view?.close()
    }

    // добавить новую карту, отсутствующую в предложенных
    override fun onAddNewCardButtonClick() {

        view?.startBarcodeScannerActivity()
        //view?.close()
    }
}