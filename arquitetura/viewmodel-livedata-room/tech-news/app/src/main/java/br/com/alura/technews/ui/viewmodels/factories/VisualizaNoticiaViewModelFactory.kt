package br.com.alura.technews.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel

class VisualizaNoticiaViewModelFactory(
    private val noticiaId: Long,
    private val noticiaRepository: NoticiaRepository
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        VisualizaNoticiaViewModel(noticiaId, noticiaRepository) as T
}