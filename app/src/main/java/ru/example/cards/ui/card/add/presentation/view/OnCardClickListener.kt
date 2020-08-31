package ru.example.cards.ui.card.add.presentation.view

// OnCardClickListener будет реализован AddCardActivityKt
// имплетемтацию итерфейса будет хранить AddCardAdapter
// по нажатию на карту из вшитого списка карт, передаем управление активити
interface OnCardClickListener {

    fun onCardClick(shopName: String, imageName: String)
}