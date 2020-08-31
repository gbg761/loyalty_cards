package ru.example.cards.ui.card.card.fragments.presentation.adapter

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter.CardAdditionallyFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.view.impl.CardAdditionallyFragmentImpl
import ru.example.cards.ui.card.card.fragments.presentation.barcode.presenter.CardBarcodeFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.barcode.view.impl.CardBarcodeFragmentImpl
import ru.example.cards.ui.card.card.fragments.presentation.factory.CardFragmentPresenterFactory

class SectionsPagerAdapter(
    private val context: Context, fm: FragmentManager,
    intent: Intent
) :
    FragmentPagerAdapter(fm) {

    // map в которой хранятся созданные презентеры для каждого фрагмента
    private val map = CardFragmentPresenterFactory
        .createAllCardFragmentPresenters(context, intent)

    //сообщает, какой фрагмент должен отображаться на каждой странице
    override fun getItem(position: Int): Fragment? {

        when (position) {

            0 -> return CardBarcodeFragmentImpl(map[Constants.KEY_PRESENTER_BARCODE] as CardBarcodeFragmentPresenter)

            1 -> return CardAdditionallyFragmentImpl(map[Constants.KEY_PRESENTER_ADDITIONALLY] as CardAdditionallyFragmentPresenter)
        }
        return null
    }

    // определяет, сколько страниц должен содержать ViewPager
    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {

        when (position) {

            0 -> return context.resources.getText(R.string.barcode)

            1 -> return context.resources.getText(R.string.additionally)
        }
        return null
    }

    fun updateBarcode(cardNumber: String?) {
        (map[Constants.KEY_PRESENTER_BARCODE] as CardBarcodeFragmentPresenter).updateBarcode(cardNumber)
    }

    fun updateCardPhoto(data: Intent) {
        (map[Constants.KEY_PRESENTER_ADDITIONALLY] as CardAdditionallyFragmentPresenter).updateCardPhoto(data)
    }
}