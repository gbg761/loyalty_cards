package ru.example.cards.model.cards.manager.factory

import android.content.Context
import ru.example.cards.model.cards.manager.impl.CardManagerImpl
import ru.example.cards.model.cards.storage.modules.impl.StorageModuleImpl
import ru.example.cards.model.cards.storage.services.StorageService

class CardManagerFactory {

    companion object {

        fun createCardManager(context: Context): CardManagerImpl {
            val storageService = StorageService(context)
            val storageModule =
                StorageModuleImpl(
                    storageService
                )
            return CardManagerImpl(
                context,
                storageModule
            )
        }
    }
}