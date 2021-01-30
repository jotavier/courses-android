package br.com.alura.leilao.models.builders;

import br.com.alura.leilao.models.Lance;
import br.com.alura.leilao.models.Leilao;
import br.com.alura.leilao.models.Usuario;

public class LeilaoBuilder {
    private final Leilao leilao;

    public LeilaoBuilder(String descricao) {
        leilao = new Leilao(descricao);
    }

    public LeilaoBuilder lance(Usuario usuario, double valor) {
        leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public Leilao constroi() {
        return leilao;
    }
}
