package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ListaPagamentosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import kotlinx.android.synthetic.main.lista_pagamentos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListaPagamentosFragment : Fragment() {
    private val estadoAppViewModel by sharedViewModel<EstadoAppViewModel>()
    private val viewModel by inject<PagamentoViewModel>()
    private val adapter by inject<ListaPagamentosAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.lista_pagamentos,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes =
            ComponentesVisuais(appBar = true, bottomNavigation = true)
        lista_pagamentos_recyclerview.adapter
        viewModel.todos().observe(viewLifecycleOwner, Observer { pagamentos ->
            pagamentos?.let { adapter.add(it) }
        })
    }
}