package ru.example.cards.model.cards.storage.services

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class StorageService(context: Context) {

    val realm: Realm

    init {
        Realm.init(context)

        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name("cardmall.realm")
                .build()
        )
        realm = Realm.getDefaultInstance()
    }

    fun disconnect() = realm.close()
}