package ru.example.cards.ui.base.presenter

import ru.example.cards.ui.base.view.MvpView

// Интерфейс, который будет реализован всеми презентерами, которые будут работать по MVP
interface MvpPresenter<V : MvpView> {

    // метод для передачи view презентеру. Т.е. view вызовет его и передаст туда себя
    fun onAttachView(mvpView: V)

    // сигнал презентеру о том, что view готово к работе. Презентер может начинать, например, загружать данные
    fun viewIsReady()

    // презентер должен отпустить view. Вызывается, например, при повороте экрана,
    // когда уничтожается старый экземпляр Activity, или при закрытии Activity.
    // Презентер должен обнулить ссылку на Activity
    fun onDetachView()
}