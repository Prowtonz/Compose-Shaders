package com.nicholas.composeshaders.services.preferencemanager

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("wallpaperPreferences")

class PreferenceManager(context: Context) {

    private val selectedShaderIdPreference = intPreferencesKey("selectedShaderId")

    private var context = context as Context?
        get() {
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                field?.createDeviceProtectedStorageContext() else field
        }

    suspend fun setSelectedShader(shaderId: Int) {
        context!!.datastore.edit { it[selectedShaderIdPreference] = shaderId }
    }

    fun getSelectedShader() = context!!
        .datastore
        .data
        .map { it[selectedShaderIdPreference] ?: 0 }

    fun release() {
        context = null
    }

}