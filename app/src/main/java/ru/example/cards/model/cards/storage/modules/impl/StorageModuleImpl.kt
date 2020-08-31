package ru.example.cards.model.cards.storage.modules.impl

import android.util.Log
import io.realm.Realm
import ru.example.cards.model.cards.OnSaveCardListener
import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.storage.entity.CardEntity
import ru.example.cards.model.cards.storage.modules.ConvertFactory
import ru.example.cards.model.cards.storage.modules.StorageModule
import ru.example.cards.model.cards.storage.services.StorageService
import java.util.*


class StorageModuleImpl(private val storageService: StorageService) :
    StorageModule {

    private val realm: Realm = storageService.realm

    override fun addNewCard(card: Card): Boolean {

        try {
            realm.beginTransaction()
            realm.insert(
                CardEntity(
                    generateCardId(),
                    card.number,
                    card.shopName,
                    card.description,
                    card.imageName,
                    card.imageColor,
                    card.frontSidePath,
                    card.backSidePath
                )
            )
            realm.commitTransaction()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun addNewCard(card: Card, onSaveCardListener: OnSaveCardListener) {

        val cardId = generateCardId()
        try {
            realm.beginTransaction()
            realm.insert(
                CardEntity(
                    cardId,
                    card.number,
                    card.shopName,
                    card.description,
                    card.imageName,
                    card.imageColor,
                    card.frontSidePath,
                    card.backSidePath
                )
            )
            realm.commitTransaction()
            onSaveCardListener.onSaveSuccess(cardId)
        } catch (e: Exception) {
            e.printStackTrace()
            onSaveCardListener.onSaveFailure()
        }
    }

    override fun updateCard(card: Card): Boolean {

        try {
            realm.beginTransaction()
            realm.insertOrUpdate(
                ConvertFactory.fromCardToCardEntity(
                    card
                )
            )
            realm.commitTransaction()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun updateCardAsync(card: Card) {
        realm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(
                ConvertFactory.fromCardToCardEntity(
                    card
                )
            )
        }
    }

    override fun getCardList(): List<Card> =
        ConvertFactory.fromCardEntityListToCardList(
            realm,
            realm.where(CardEntity::class.java).findAll()
        )

    override fun getCardListSize(): Long = realm.where(CardEntity::class.java).count()

    override fun getCard(id: Long): Card? {
        val cardEntity = findFirstCardEntity(id)
        if (cardEntity != null) return ConvertFactory.fromCardEntityToCard(
            cardEntity
        )
        else return null
    }

    override fun deleteCard(id: Long) {
        realm.executeTransaction {
            findFirstCardEntity(id)?.deleteFromRealm()
        }
    }

    override fun searchCardsByTextQuery(query: String): List<Card> {

        val pattern = "*$query*"
        return ConvertFactory.fromCardEntityListToCardList(
            realm, realm.where(CardEntity::class.java)
                .like("itemName", pattern)
                .findAll()
        )
    }

    override fun deleteAllCard() {
        val results = realm.where(CardEntity::class.java).findAll()
        realm.executeTransaction {
            results.deleteAllFromRealm()
        }
    }

    private fun findFirstCardEntity(id: Long) =
        realm.where(CardEntity::class.java).equalTo("id", id).findFirst()

    override fun disconnect() = storageService.disconnect()

    private fun generateCardId(): Long = Date().time
}