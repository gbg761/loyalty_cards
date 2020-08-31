package ru.example.cards.ui.card.card.fragments.presentation.additionally.presenter

import android.content.Intent
import ru.example.cards.ui.base.presenter.MvpPresenter
import ru.example.cards.ui.card.card.fragments.CardFragmentPresenter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.view.CardAdditionallyFragment

interface CardAdditionallyFragmentPresenter :
    CardFragmentPresenter,
    MvpPresenter<CardAdditionallyFragment> {

    fun onTakeCardPhotoClick(side: String)

    fun updateCardPhoto(data: Intent)
}