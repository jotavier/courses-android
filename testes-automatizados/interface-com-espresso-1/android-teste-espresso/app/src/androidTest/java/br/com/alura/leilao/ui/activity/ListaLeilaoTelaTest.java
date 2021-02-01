package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.R;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static br.com.alura.leilao.matchers.ViewMatcher.apareceLeilaoNaPosicao;
import static org.junit.Assert.fail;

public class ListaLeilaoTelaTest {
    private static final String ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API = "Banco de dados não foi limpo";
    private static final String LEILAO_NAO_FOI_SALVO = "Leilão não foi salvo: ";

    // initialTouchMode -> inicia teste com modo de toque
    // launchActivity -> inicia aplicação automaticamente no início do teste
    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);
    private final TesteWebClient webClient = new TesteWebClient();

    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    @Test
    public void deve_AparecerLeilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0,
                        "Carro",
                        0.0)));
    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        tentaSalvarLeilaoNaApi(
                new Leilao("Carro"),
                new Leilao("Computador"));

        activity.launchActivity(new Intent());

        /*  Irá fazer a buscar com um id específico, ou seja,
            neste caso, outras views com o mesmo texto, porém ids diferentes
            não serão contabilizadas.
            Com esta metodologia, views com o mesmo texto acarretarão em
            exception.

            onView(allOf(withText("Carro"), withId(R.id.item_leilao_descricao))).check(matches(isDisplayed()));
            String formatoEsperadoCarro = formatadorDeMoeda.formata(0.0);
            onView(allOf(withText(formatoEsperadoCarro), withId(R.id.item_leilao_maior_lance)))
                        .check(matches(isDisplayed()));

            onView(allOf(withText("Computador"), withId(R.id.item_leilao_descricao))).check(matches(isDisplayed()));
            String formatoEsperadoComputador = formatadorDeMoeda.formata(0.0);
            onView(allOf(withText(formatoEsperadoComputador), withId(R.id.item_leilao_maior_lance)))
                          .check(matches(isDisplayed()));
         */


        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0,
                        "Carro",
                        0.0)));
        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(1,
                        "Computador",
                        0.0)));
    }


    @After
    public void tearDown() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    private void tentaSalvarLeilaoNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                fail(LEILAO_NAO_FOI_SALVO + leilao.getDescricao());
            }
        }
    }

    private void limpaBancoDeDadosDaApi() throws IOException {
        boolean bancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();

        if (bancoDeDadosNaoFoiLimpo) {
            fail(ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API);
        }
    }
}