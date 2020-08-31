package ru.example.cards.ui.card.card.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import ru.example.cards.ui.base.presenter.BasePresenter
import ru.example.cards.ui.card.card.fragments.interactor.CardFragmentInteractor

abstract class BaseCardFragmentPresenter<V : CardFragment>(
    private val context: Context,
    private val interactor: CardFragmentInteractor
) :
    BasePresenter<V>() {

    // получаем имя изображение карты
    fun getImageName() = interactor.getImageName()

    // размеры изображения произвольной карты
    // исходя из этих размеров создадим bitmap(который закрасим произвольным цветом),
    // который будем использовать как изображение карт, добавленных юзером
    // не из встроенных
    fun getBitmapSize(): Point {

        val drawableId = context.resources.getIdentifier(
            "abk",
            "drawable",
            context.packageName
        )
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
        val point = Point(bitmap.width, bitmap.height)
        bitmap.recycle()
        return point
    }
}