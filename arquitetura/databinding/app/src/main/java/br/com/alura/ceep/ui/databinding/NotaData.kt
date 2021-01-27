package br.com.alura.ceep.ui.databinding

import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.model.Nota

data class NotaData(
    private var nota: Nota = Nota(),
    val titulo: MutableLiveData<String> = MutableLiveData<String>().also { it.value = nota.titulo },
    val descricao: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = nota.descricao
    },
    val imagemUrl: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = nota.imagemUrl
    },
    val favorita: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = nota.favorita
    }
) {

    fun atualiza(nota: Nota) {
        this.nota = nota
        titulo.value = this.nota.titulo
        descricao.value = this.nota.descricao
        favorita.value = this.nota.favorita
        imagemUrl.value = this.nota.imagemUrl
    }

    fun paraNota(): Nota? {
        return this.nota.copy(
            titulo = titulo.value ?: return null,
            descricao = descricao.value ?: return null,
            favorita = favorita.value ?: return null,
            imagemUrl = imagemUrl.value ?: return null
        )
    }
}