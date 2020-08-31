package ru.example.cards.model.cards.storage.modules

import io.realm.Realm
import io.realm.RealmResults
import ru.example.cards.model.cards.models.Card
import ru.example.cards.model.cards.storage.entity.CardEntity

class ConvertFactory {

    companion object {

        fun fromCardToCardEntity(card: Card): CardEntity =
            CardEntity(
                card.id,
                card.number,
                card.shopName,
                card.description,
                card.imageName,
                card.imageColor,
                card.frontSidePath,
                card.backSidePath,
                card.lastUsedDate
            )

        fun fromCardEntityToCard(cardEntity: CardEntity): Card =
            Card(
                cardEntity.id,
                cardEntity.number,
                cardEntity.shopName,
                cardEntity.cardDescription,
                cardEntity.imageName,
                cardEntity.imageColor,
                cardEntity.cardFrontSidePath,
                cardEntity.cardBackSidePath,
                cardEntity.lastUsedDate
            )

        fun fromCardEntityListToCardList(
            realm: Realm,
            realmResults: RealmResults<CardEntity>
        ): List<Card> {

            val cardEntityList: List<CardEntity> = realm.copyFromRealm(realmResults)
            return cardEntityList.map {
                Card(
                    it.id,
                    it.number,
                    it.shopName,
                    it.cardDescription,
                    it.imageName,
                    it.imageColor,
                    it.cardFrontSidePath,
                    it.cardBackSidePath,
                    it.lastUsedDate
                )
            }
        }
    }
}