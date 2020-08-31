package ru.example.cards.model.cards

// используется при создании карты:
// CreateCardPresenterImpl вызывает метод сохранения карты в хранилище,
// который в случае удачного сохранения через метод onSaveSuccess(cardId: Long) получает id добавленной карты, иначе
// вызывается метод onSaveFailure() в случае ошибки сохранения
interface OnSaveCardListener {

    fun onSaveSuccess(cardId: Long)

    fun onSaveFailure()
}