package ru.example.cards.ui.card.card.fragments.interactor.impl

import android.net.Uri
import android.util.Log
import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.manager.CardManager
import ru.example.cards.ui.card.card.fragments.interactor.CardFragmentInteractor

class CardFragmentInteractorImpl(
    private val manager: CardManager,
    cardId: Long?,
    sharedCardUri: Uri?
) :
    CardFragmentInteractor {

    private val TAG = "CardFragmentInteractor"

    var card: Card? = null

    init {

        if (cardId != null) {

            card = manager.getCard(cardId)
            Log.d(TAG, card.toString())

        } else if (sharedCardUri != null) {
            card = manager.getCardFromShareableFile(sharedCardUri)
            if (card != null) {
                manager.save(card!!)
            }
        }

    }

    // photoSides содержит ссылки на фотографии сторон карт
    // обновляем карту в бд: добавляем расположение фотографий
    // каждой из сторон карты
    override fun updateCard(frontSidePath: String?, backSidePath: String?) {

        // если условие true, не необходимости обращаться в бд
        // и записывать пустые данные
        if (frontSidePath == null && backSidePath == null) {
            return
        } else {

            card?.let {

                // обновляем поля карты
                it.frontSidePath = frontSidePath
                it.backSidePath = backSidePath

                // обновляем карту в бд
                val isSave = manager.save(it)
                Log.d(TAG, "is save = $isSave")
            }
        }

    }

    // проверяет, что фото карты, расположенная по адресу cardPhotoPath, существует на диске
    private fun getCardPhotoPath(cardPhotoPath: String?): String? =

        if (cardPhotoPath != null) {

            if (manager.isCardPhotoExist(cardPhotoPath))
                cardPhotoPath
            else
                null
        } else
            null

    override fun getShopName() = card?.shopName

    // возвращает абслолютный путь до фото лицевой стороны карты
    override fun getCardFrontSidePhotoPath() = getCardPhotoPath(card?.frontSidePath)

    // возвращает абслолютный путь до фото обратной стороны карты
    override fun getCardBackSidePhotoPath() = getCardPhotoPath(card?.backSidePath)

    override fun getImageName() = card?.imageName

    override fun getCardColor() = card?.imageColor


    override fun getCardNumber() = card?.number

    override fun getCardDescription() = card?.description

    override fun shareCard() = manager.shareCard(card)

}