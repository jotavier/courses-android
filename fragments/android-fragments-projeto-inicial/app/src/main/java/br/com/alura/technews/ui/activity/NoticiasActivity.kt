package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment
import kotlinx.android.synthetic.main.activity_noticias.*


class NoticiasActivity : AppCompatActivity() {

    companion object {
        private const val VISUALIZA_NOTICIA_TAG = "visualiza-noticia"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        configuraFragmentPeloEstado(savedInstanceState)
    }

    private fun configuraFragmentPeloEstado(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            abreListaNoticias()
            return
        }
        tentaReabrirFragmentVisualizaNoticia()
    }

    private fun tentaReabrirFragmentVisualizaNoticia() {
        supportFragmentManager.findFragmentByTag(VISUALIZA_NOTICIA_TAG)?.let { fragment ->
            val argumentos = fragment.arguments
            val novoFragment = VisualizaNoticiaFragment()
            novoFragment.arguments = argumentos
            remove(fragment)
            transacaoFragment {
                val container = configuraContainerFragmentVisualizaNoticia()
                replace(container, novoFragment, VISUALIZA_NOTICIA_TAG)
            }
        }
    }

    private fun FragmentTransaction.configuraContainerFragmentVisualizaNoticia(): Int {
        if (activity_noticias_container_visualiza_noticia != null) {
            return R.id.activity_noticias_container_visualiza_noticia
        }
        addToBackStack(null)
        return R.id.activity_noticias_container_lista_noticias
    }

    private fun abreListaNoticias() {
        transacaoFragment {
            add(R.id.activity_noticias_container_lista_noticias, ListaNoticiasFragment())
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaNoticiasFragment -> {
                configuraListaNoticias(fragment)
            }
            is VisualizaNoticiaFragment -> {
                configuraVisualizaNoticia(fragment)
            }
        }
    }

    private fun configuraVisualizaNoticia(fragment: VisualizaNoticiaFragment) {
        fragment.quandoFinaliza = {
            supportFragmentManager.findFragmentByTag(VISUALIZA_NOTICIA_TAG)?.let { fragment ->
                remove(fragment)
            }
        }
        fragment.quandoSelecionaMenuEdicao = this::abreFormularioEdicao
    }

    private fun remove(fragment: Fragment) {
        transacaoFragment { remove(fragment) }
        supportFragmentManager.popBackStack()
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.quandoNoticiaSelecionada = this::quandoNoticiaSelecionada
        fragment.quandoFabAdicionaNoticiaClicado = this::quandoFabAdicionaNoticiaClicado
    }

    private fun quandoNoticiaSelecionada(noticia: Noticia) {
        val visualizaNoticiaFragment = VisualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticia.id)
        visualizaNoticiaFragment.arguments = bundle
        transacaoFragment {
            val container = configuraContainerFragmentVisualizaNoticia()
            replace(container, visualizaNoticiaFragment, VISUALIZA_NOTICIA_TAG)
        }
    }


    private fun quandoFabAdicionaNoticiaClicado() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }
}
