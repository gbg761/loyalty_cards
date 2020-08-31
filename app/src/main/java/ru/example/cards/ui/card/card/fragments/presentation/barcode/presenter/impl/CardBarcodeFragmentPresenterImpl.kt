package ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.impl

import android.content.Context
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import ru.example.cards.ui.card.card.fragments.interactor.CardFragmentInteractor
import ru.example.cards.ui.card.card.fragments.BaseCardFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.dialogs.ReportBugDialogFragment
import ru.example.cards.ui.card.card.fragments.presentation.barcode.generator.BarcodeGenerator
import ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.CardBarcodeFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.CardBarcodeFragment
import ru.example.cards.utils.dpToPx

class CardBarcodeFragmentPresenterImpl(
    private val context: Context,
    private val interactor: CardFragmentInteractor
) :
    BaseCardFragmentPresenter<CardBarcodeFragment>(context, interactor),
    CardBarcodeFragmentPresenter {

    override fun viewIsReady() {

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

        val cardNumber = interactor.getCardNumber()
        setBarcode(cardNumber)
    }

    override fun updateBarcode(cardNumber: String?) {
        setBarcode(cardNumber)
    }

    override fun shareCard() {

        interactor.shareCard()?.let {
            view?.share(it)
        }
    }

    override fun pressedReportBugButton() {
        view?.showDialog(ReportBugDialogFragment())
    }

    private fun generateBarcode(cardNumber: String): Bitmap? {

        // cardNumber содержит контрольный символ
        // отбрасываем его, т.к. он не нужен для отображения
        //val numberWithoutCheckDigit = cardNumber.substring(0, cardNumber.lastIndex).replace("\\s".toRegex(), "")
        val numberWithoutCheckDigit = cardNumber.replace("\\s".toRegex(), "")

        val imgWidth = 250 // dp
        val imgHeight = 100 // dp

        return BarcodeGenerator().encodeAsBitmap(
            contents = numberWithoutCheckDigit,
            format = BarcodeFormat.CODE_128,
            imgWidth = imgWidth.dpToPx(context.resources.displayMetrics),
            imgHeight = imgHeight.dpToPx(context.resources.displayMetrics)
        )
    }

    private fun setBarcode(cardNumber: String?) {

        if (cardNumber != null) {

            val barcode = generateBarcode(cardNumber)
            if (barcode != null)
                setCardBarcode(barcode)

            setCardNumber(cardNumber)
        }
    }


    private fun setCardBarcode(barcode: Bitmap) {
        view?.setCardBarcode(barcode)
    }

    private fun setCardNumber(cardNumber: String) {
        view?.setCardNumber(cardNumber)
    }
}