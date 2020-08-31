package ru.example.cards.model.cards.manager.impl

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.gson.Gson
import ru.example.cards.BuildConfig
import ru.example.cards.R
import ru.example.cards.model.cards.OnSaveCardListener
import ru.example.cards.model.cards.manager.CardManager
import ru.example.cards.model.cards.manager.reader.Reader
import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.models.InputCard
import ru.example.cards.model.cards.models.ShareableCard
import ru.example.cards.model.cards.storage.modules.StorageModule
import ru.example.cards.model.helper.FileHelper
import ru.example.cards.utils.toCard
import ru.example.cards.utils.toShareableCard
import java.io.File
import java.util.*

class CardManagerImpl(
    private val context: Context,
    private val storageModule: StorageModule
) : CardManager {

    private lateinit var inputCardList: List<InputCard>

    private var cards: MutableList<Card> = storageModule.getCardList().toMutableList()

    override fun save(card: Card): Boolean {
        // проверяем данные на валидность
        if (validateData(card)) {
            // если id == 0, создаем новую карту
            return if (card.id == 0L) {
                storageModule.addNewCard(card)
            } else {
                storageModule.updateCard(card)
            }
        }
        return false
    }

    override fun save(card: Card, onSaveCardListener: OnSaveCardListener) = storageModule.addNewCard(card, onSaveCardListener)

    // передаем в виджет первые 12 элементов списка
    override fun getCards() = cards.take(12).toMutableList()

    override fun updateCardsForWidget() {

        // получаем обновленные данные из хранилища
        cards = getCardList().toMutableList()

        // сортируем по убыванию даты использования
        cards.sortWith(Comparator { p0, p1 ->
            p1!!.lastUsedDate.compareTo(p0!!.lastUsedDate)
        })
    }

    override fun getCardList() = storageModule.getCardList()

    override fun getCardListSize() = storageModule.getCardListSize()

    override fun getCard(id: Long) = storageModule.getCard(id)

    override fun searchCardsByTextQuery(query: String): List<Card> = storageModule.searchCardsByTextQuery(query)

    override fun deleteCard(id: Long) = storageModule.deleteCard(id)

    override fun deleteAllCard() = storageModule.deleteAllCard()

    override fun disconnect() = storageModule.disconnect()

    override fun updateCardAsync(card: Card) = storageModule.updateCardAsync(card)

    override fun shareCard(card: Card?): Intent? {

        if (card != null) {
            val file = getShareableFile(card)

            if (file != null) {

                val fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)

                val intent = Intent(Intent.ACTION_SEND)

                val playStoreLink = context.getString(R.string.sharing_card_offer) + context.packageName
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    playStoreLink
                )
                intent.type = "application/octet-stream"
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intent.putExtra(Intent.EXTRA_STREAM, fileUri)

                return intent
            }
        }
        return null
    }

    // создаем
    private fun getShareableFile(card: Card): File? {

        val shareableCard = card.toShareableCard()

        //  конвертируем из объекта в строку json
        val jsonFromCard = Gson().toJson(shareableCard)

        // сохраняем всю информацию о карте в кеш приложения
        return FileHelper().createFile(context, card.shopName, jsonFromCard)

    }

    override fun getCardFromShareableFile(sharedCardUri: Uri): Card? {

        val jsonFomCard = FileHelper().readFromFile(context, sharedCardUri)
        return if (jsonFomCard != null)
            Gson().fromJson(jsonFomCard, ShareableCard::class.java).toCard()
        else
            null
    }

    private fun validateData(card: Card): Boolean = !(("" == card.shopName).or("" == card.number))

    // метод чтения из csv файла
    override fun read() {
        if (!(this::inputCardList.isInitialized)) {
            inputCardList = Reader(context).read()
        }
    }

    override fun getInputCardList(): List<InputCard> {
        if (!(this::inputCardList.isInitialized))
            throw IllegalArgumentException("Список карт не проинициализирован")
        return inputCardList
    }

    override fun checkInternetConnection(): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    // проверяет существование фотографии карт
    override fun isCardPhotoExist(path: String) = File(path).exists()
}
