package ru.example.cards.ui.card.card.fragments.presentation.additionally.view.impl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_card_additionally.view.*
import kotlinx.android.synthetic.main.card_side.view.*
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.CardAdditionallyFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.view.CardAdditionallyFragment

class CardAdditionallyFragmentImpl(private val presenter: CardAdditionallyFragmentPresenter) :
    Fragment(),
    CardAdditionallyFragment {

    private lateinit var cardImageView: ImageView // выводим изображение карты
    private lateinit var cardShopNameTextView: TextView // выводим название магазина в случае, если у карты отсуствует изображение
    private lateinit var frontSide: CardView // фото лицевой стороны карты
    private lateinit var backSide: CardView // фото обратной стороны карты
    private lateinit var descriptionTextView: TextView

    private var listener: OnTakeCardPhotoClickListener? = null

    interface OnTakeCardPhotoClickListener {
        fun onTakeCardPhotoClick(args: Bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter.onAttachView(this)

        val view = inflater.inflate(R.layout.fragment_card_additionally, container, false)
        initUI(view)
        presenter.viewIsReady()
        return view
    }

    private fun findViewById(view: View) {

        cardImageView = view.cardImageView
        cardShopNameTextView = view.shopNameTextView
        frontSide = view.frontSide as CardView
        backSide = view.backSide as CardView
        descriptionTextView = view.descriptionTextView
    }

    private fun initUI(view: View) {

        findViewById(view)

        val frontSideDescription = context?.getString(R.string.front_side)
        frontSide.sideDescription.text = frontSideDescription
        frontSide.setOnClickListener {
            // открыть экран фотографирования карты с лицевой стороны
            presenter.onTakeCardPhotoClick(frontSideDescription!!)
        }

        val backSideDescription = context?.getString(R.string.back_side)
        backSide.sideDescription.text = backSideDescription
        backSide.setOnClickListener {
            // открыть экран фотографирования карты с обратной стороны
            presenter.onTakeCardPhotoClick(backSideDescription!!)
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

    override fun setCardPhoto(image: Uri, side: Constants.SIDES) =

        when (side) {

            Constants.SIDES.FRONT -> {
                frontSide.actionLinearLayout.visibility = View.GONE
                frontSide.photo.visibility = View.VISIBLE
                frontSide.photo.setImageURI(image)
            }

            Constants.SIDES.BACK -> {
                backSide.actionLinearLayout.visibility = View.GONE
                backSide.photo.visibility = View.VISIBLE
                backSide.photo.setImageURI(image)
            }
        }


    override fun setCardDescription(description: String) {
        descriptionTextView.text = description
    }

    // запускаем активити фотографирования карты
    override fun takeCardPhotos(args: Bundle) {
        listener?.onTakeCardPhotoClick(args)
    }

    override fun close() {
        // do nothing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            listener = context as OnTakeCardPhotoClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnTakeCardPhotoButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}