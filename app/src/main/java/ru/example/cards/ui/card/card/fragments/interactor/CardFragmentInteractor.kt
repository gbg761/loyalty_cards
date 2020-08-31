package ru.example.cards.ui.card.card.fragments.interactor

import android.content.Intent

interface CardFragmentInteractor {

    fun getShopName(): String?

    fun getImageName(): String?

    fun getCardColor(): Int?

    fun getCardNumber(): String?

    fun getCardDescription(): String?

    fun getCardFrontSidePhotoPath(): String?

    fun getCardBackSidePhotoPath(): String?

    fun shareCard(): Intent?

    fun updateCard(frontSidePath: String?, backSidePath: String?)
}