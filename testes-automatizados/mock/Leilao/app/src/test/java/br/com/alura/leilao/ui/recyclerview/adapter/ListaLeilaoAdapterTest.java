package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

// Garante que o Mockito.initMocks será chamado a cada teste,
// Remove a necessidade de chamar dentro dos métodos
@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    // Por meio de injeção de dependencia, cria um contexto mock;
    @Mock
    private Context context;
    // Retira a necessidade do Mockito.spy, avisando
    // que este será um objeto espião
    @Spy
    private final ListaLeilaoAdapter listaLeilaoAdapter = new ListaLeilaoAdapter(context);
    // Cria um objeto mockado a partir do nome de uma classe
    // Context context = Mockito.mock(Context.class);
    // "Espia" o objeto para lidar com seus tratamentos.
    // O objeto espião é um objeto mockado.
    // ListaLeilaoAdapter listaLeilaoAdapter = Mockito.spy(new ListaLeilaoAdapter(context));

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        // Inicializa os objetos mockados injetados
        // MockitoAnnotations.initMocks(this);
        // Avisa que, durante a execução, nada serão feito durante a chamada de algum método
        // Não consegue lidar com métodos final portanto, para estes casos, encapsula-se
        // O método final dentro de um método criado na aplicação.
        doNothing().when(listaLeilaoAdapter).atualizaLista();
        listaLeilaoAdapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computaodr")
        )));

        int quantidadeLeiloesDevolvida = listaLeilaoAdapter.getItemCount();

        // Verifica se o método atualizaLista foi chamado
        verify(listaLeilaoAdapter).atualizaLista();

        assertThat(quantidadeLeiloesDevolvida, is(2));
    }
}