package ru.example.cards.ui.card.card.fragments

import android.graphics.Bitmap
import ru.example.cards.ui.base.view.MvpView

interface CardFragment : MvpView {

    fun setCardImage(imageName: String)

    fun setCardImageBitmap(bitmap: Bitmap)

    fun setShopName(shopName: String)
}