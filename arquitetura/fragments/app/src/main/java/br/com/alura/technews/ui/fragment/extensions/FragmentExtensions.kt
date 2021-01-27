package br.com.alura.technews.ui.fragment.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.mostraErro(mensagem: String) {
    Toast.makeText(
        this.requireContext(),
        mensagem,
        Toast.LENGTH_LONG
    ).show()
}