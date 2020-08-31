package ru.example.cards.ui.card.card.fragments.presentation.barcode.view.impl

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_card_barcode.view.*
import ru.example.cards.R
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.dialogs.ReportBugDialogFragment
import ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.CardBarcodeFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.CardBarcodeFragment


class CardBarcodeFragmentImpl(private val presenter: CardBarcodeFragmentPresenter) :
    Fragment(),
    CardBarcodeFragment {

    private lateinit var cardImageView: ImageView // выводим изображение карты
    private lateinit var cardShopNameTextView: TextView // выводим название магазина в случае, если у карты отсуствует изображение
    private lateinit var barcodeImageView: ImageView // выводим штрих-код
    private lateinit var cardNumberTextView: TextView // выводим номер карты

    private lateinit var shareButton: View //
    private lateinit var reportButton: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter.onAttachView(this)
        val view = inflater.inflate(R.layout.fragment_card_barcode, container, false)

        initUI(view)

        presenter.viewIsReady()
        return view
    }

    private fun findViewById(view: View) {

        cardImageView = view.cardImageView
        cardShopNameTextView = view.shopNameTextView
        cardNumberTextView = view.cardNumberTextView
        barcodeImageView = view.barcodeImageView

        shareButton = view.shareButton
        reportButton = view.reportButton
    }

    private fun initUI(view: View) {

        findViewById(view)

        shareButton.setOnClickListener {
            presenter.shareCard()
        }

        reportButton.setOnClickListener {
            presenter.pressedReportBugButton()
        }
    }

    override fun setCardImage(imageName: String) {

        // получить id изображения карты по имени файла
        val drawableId = context?.resources?.getIdentifier(
            imageName,
            "drawable",
            context!!.packageName
        )

        drawableId?.let {
            cardImageView.setImageResource(it)
        }
    }

    override fun setCardImageBitmap(bitmap: Bitmap) {
        cardImageView.setImageBitmap(bitmap)
    }

    override fun setShopName(shopName: String) {
        cardShopNameTextView.visibility = View.VISIBLE
        cardShopNameTextView.text = shopName
    }

    override fun setCardBarcode(barcodeImage: Bitmap) =
        barcodeImageView.setImageBitmap(barcodeImage)

    override fun setCardNumber(number: String) {
        cardNumberTextView.text = number
    }

    override fun share(intent: Intent) {
        startActivity(Intent.createChooser(intent,getString(R.string.share)))
    }

    override fun showDialog(dialog: DialogFragment) {
        (dialog as ReportBugDialogFragment).show(fragmentManager, "report_dialog_fragment")
    }

    override fun close() {
        // do nothing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }
}
