package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class LoginRepository(
    private val preferences: SharedPreferences
) {
    private companion object {
        const val CHAVE_LOGADO = "LOGADO"
    }

    fun loga() = salva(true)

    fun estaLogado() = preferences.getBoolean(CHAVE_LOGADO, false)

    fun desloga() = salva(false)

    private fun salva(loga: Boolean) {
        preferences.edit {
            putBoolean(CHAVE_LOGADO, loga)
        }
    }
}
