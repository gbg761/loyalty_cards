package ru.example.cards.ui.card.card.fragments.presentation.barcode.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.DialogFragment
import ru.example.cards.ui.card.card.fragments.CardFragment

interface CardBarcodeFragment :
    CardFragment {

    fun setCardBarcode(barcodeImage: Bitmap)

    fun setCardNumber(number: String)

    fun share(intent: Intent)

    fun showDialog(dialog: DialogFragment)
}