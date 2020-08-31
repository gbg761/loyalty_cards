package ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.impl

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.net.toUri
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.fragments.interactor.CardFragmentInteractor
import ru.example.cards.ui.card.card.fragments.BaseCardFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.CardAdditionallyFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.view.CardAdditionallyFragment

class CardAdditionallyPresenterImpl(context: Context, private val interactor: CardFragmentInteractor) :
    BaseCardFragmentPresenter<CardAdditionallyFragment>(context, interactor),
    CardAdditionallyFragmentPresenter {

    override fun viewIsReady() {
        setCard()
    }

    private fun setCard() {

        val imageName = getImageName()

        // если false, значит, что юзер решил создать карту не из встроенных и у нее не будет изображения
        // в этом случае покрасим карточку в однотонный цвет
        if (imageName != null) {
            view?.setCardImage(imageName)
        } else {

            val bitmapSizePoint = getBitmapSize()
            val bitmap =
                Bitmap.createBitmap(bitmapSizePoint.x, bitmapSizePoint.y, Bitmap.Config.RGB_565)

            // закрасим bitmap
            interactor.getCardColor()?.let {color ->
                bitmap.eraseColor(color)
            }

            view?.setCardImageBitmap(bitmap)

            interactor.getShopName()?.let { shopName ->
                view?.setShopName(shopName)
            }
        }

        // устанавливаем описание карты
        interactor.getCardDescription()?.let {
            view?.setCardDescription(it)
        }

        // устанавливаем фото сторон карты
        // front side
        interactor.getCardFrontSidePhotoPath()?.let {
            view?.setCardPhoto(it.toUri(), Constants.SIDES.FRONT)
        }

        // back side
        interactor.getCardBackSidePhotoPath()?.let {
            view?.setCardPhoto(it.toUri(), Constants.SIDES.BACK)
        }
    }

    override fun updateCardPhoto(data: Intent) {

        val frontSidePath = data.getStringExtra(Constants.CARD_FRONT_SIDE_EXTRA)
        val backSidePath = data.getStringExtra(Constants.CARD_BACK_SIDE_EXTRA)

        frontSidePath?.let {
            view?.setCardPhoto(it.toUri(), Constants.SIDES.FRONT)
        }

        backSidePath?.let {
            view?.setCardPhoto(it.toUri(), Constants.SIDES.BACK)
        }

        // после того, как мы получим абсолютный путь до фотографий карт
        // необходимо обновить карту в бд, добавив путь до фото
        madeCardPhotos(frontSidePath, backSidePath)
    }

    // вызывается когда юзер сделал фото карты и передал их
    // для отображения в активити
    // необходимо обновить карту
    private fun madeCardPhotos(frontSidePath: String?, backSidePath: String?) =
        interactor.updateCard(frontSidePath, backSidePath)

    override fun onTakeCardPhotoClick(side: String) {

        // помещаем в bundle сторону карты, которую хотим сфотографировать
        // дальше передаем этот bundle в активити, которая отвечает за фотографитрования карты
        // CardPhotoActivityKt
        val args = Bundle()
        args.putString(Constants.CARD_SIDE_EXTRA, side)
        view?.takeCardPhotos(args)
    }
}