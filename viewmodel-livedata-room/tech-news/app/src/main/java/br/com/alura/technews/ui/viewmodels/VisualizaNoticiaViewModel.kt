package br.com.alura.technews.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VisualizaNoticiaViewModel(
    noticiaId: Long,
    private val noticiaRepository: NoticiaRepository
) : ViewModel() {

    companion object {
        const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
        const val TITULO_APPBAR = "Notícia"
        const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"
    }

    val noticiaEncontrada = noticiaRepository.buscaPorId(noticiaId)

    fun remove() = noticiaEncontrada.value?.run { noticiaRepository.remove(this) }
        ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(dado = null, NOTICIA_NAO_ENCONTRADA)
        }
}