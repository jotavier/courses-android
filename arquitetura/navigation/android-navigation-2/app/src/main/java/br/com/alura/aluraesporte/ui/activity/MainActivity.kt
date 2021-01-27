package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val controlador by lazy { findNavController(R.id.main_activity_nav_host) }
    private val viewModel by viewModel<EstadoAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        controlador.addOnDestinationChangedListener { _: NavController,
                                                      destination: NavDestination,
                                                      _: Bundle? ->
            title = destination.label
            viewModel.componentes.observe(this, Observer { temComponentes ->
                when (temComponentes.appBar) {
                    true -> supportActionBar?.show()
                    else -> supportActionBar?.hide()
                }
                main_activity_bottom_navigation.visibility =
                    isVisible(temComponentes.bottomNavigation)
            })
        }
        main_activity_bottom_navigation.setupWithNavController(controlador)
    }

    private fun isVisible(visible: Boolean) = if (visible) View.VISIBLE else View.GONE
}
