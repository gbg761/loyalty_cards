package ru.example.cards.ui.card.card.fragments.presentation.additionally.view

import android.net.Uri
import android.os.Bundle
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.fragments.CardFragment

interface CardAdditionallyFragment :
    CardFragment {

    // запускам активити для фотографирования карты
    fun takeCardPhotos(args: Bundle)

    fun setCardDescription(description: String)

    fun setCardPhoto(image: Uri, side: Constants.SIDES)
}