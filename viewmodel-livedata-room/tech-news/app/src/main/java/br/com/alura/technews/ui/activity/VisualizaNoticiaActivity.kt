package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel.Companion.MENSAGEM_FALHA_REMOCAO
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel.Companion.NOTICIA_NAO_ENCONTRADA
import br.com.alura.technews.ui.viewmodels.VisualizaNoticiaViewModel.Companion.TITULO_APPBAR
import br.com.alura.technews.ui.viewmodels.factories.VisualizaNoticiaViewModelFactory
import kotlinx.android.synthetic.main.activity_visualiza_noticia.*


class VisualizaNoticiaActivity : AppCompatActivity() {

    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }

    private val visualizaNoticiaViewModel by lazy {
        val factory = VisualizaNoticiaViewModelFactory(noticiaId, repository)
        ViewModelProvider(this, factory).get(VisualizaNoticiaViewModel::class.java)
    }

    private val repository by lazy {
        NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
    }

    private lateinit var noticia: Noticia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_noticia)
        title = TITULO_APPBAR
        verificaIdDaNoticia()
        buscaNoticiaSelecionada()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visualiza_noticia_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.visualiza_noticia_menu_edita -> abreFormularioEdicao()
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaNoticiaSelecionada() {
        visualizaNoticiaViewModel.noticiaEncontrada.observe(this, { noticiaEncontrada ->
            noticiaEncontrada?.let { noticia ->
                this.noticia = noticia
                preencheCampos(noticia)
            }
        })
    }

    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            finish()
        }
    }

    private fun preencheCampos(noticia: Noticia) {
        activity_visualiza_noticia_titulo.text = noticia.titulo
        activity_visualiza_noticia_texto.text = noticia.texto
    }

    private fun remove() {
        visualizaNoticiaViewModel.remove().observe(this, { res ->
            res.erro?.let {
                mostraErro(MENSAGEM_FALHA_REMOCAO)
                return@observe
            }
            finish()
        })
    }

    private fun abreFormularioEdicao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }

}
