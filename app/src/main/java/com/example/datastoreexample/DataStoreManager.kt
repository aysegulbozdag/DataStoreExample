package com.example.datastoreexample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("datastore_example")

class DataStoreManager(context: Context) {

    private val myDatastore: DataStore<Preferences> = context.datastore

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
    }


    val myDatastoreWrite: suspend (Preferences.Key<String>, String) -> Unit = { key, value ->
        myDatastore.edit { preferences ->
            preferences[NAME_KEY] = value
        }
    }

    val myDatastoreRead: (Preferences.Key<String>) -> Flow<String> = { key ->
        myDatastore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }

}