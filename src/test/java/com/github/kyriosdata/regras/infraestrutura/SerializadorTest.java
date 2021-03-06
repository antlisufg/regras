package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.Pontuacao;
import com.github.kyriosdata.regras.Relato;
import com.github.kyriosdata.regras.Relatorio;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.regra.Configuracao;
import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import com.github.kyriosdata.regras.regra.RegraPontosPorRelato;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SerializadorTest {

    private static Serializador sz;

    @BeforeAll
    public static void setUpClass() {
        sz = new Serializador();
    }

    @Test
    public void serializarValor() {
        verificaValor(new Valor(LocalDate.now()));
        verificaValor(new Valor(3.14f));
        verificaValor(new Valor(true));
        verificaValor(new Valor(false));
        verificaValor(new Valor("casa"));
    }

    private void verificaValor(Valor valor) {
        String json = sz.toJson(valor);
        Valor recuperado = sz.valor(json);
        assertEquals(valor, recuperado);
    }

    @Test
    public void serializarPontuacao() {
        verificaPontuacao(new Pontuacao("a", new Valor(false)));
    }

    private void verificaPontuacao(Pontuacao pontuacao) {
        String json = sz.toJson(pontuacao);
        Pontuacao recuperado = sz.pontuacao(json);
        assertEquals(pontuacao, recuperado);
    }

    @Test
    public void serializarRegraExpressao() {
        Regra regra1 = getRegraExpressao();

        verificaRegraExpressao((RegraExpressao)regra1);
    }

    @Test
    public void serializarRegraPontosPorRelato() {
        RegraPontosPorRelato regra = getRegraPontosPorRelato();

        String json = sz.toJson(regra);
        RegraPontosPorRelato recuperada = sz.regraPontosPorRelato(json);

        assertEquals(regra, recuperada);
    }

    private RegraPontosPorRelato getRegraPontosPorRelato() {
        return new RegraPontosPorRelato("p", "d", 1f, 0f, "t", 0.5f);
    }

    private RegraExpressao getRegraExpressao() {
        return new RegraExpressao("x", "d", 1f, 0f, "1");
    }

    @Test
    public void valoresPadraoParaRegraExpressao() {
        String json = "{ \"variavel\": \"soma\", \"expressao\": \"x + y\" }";

        RegraExpressao recuperada = sz.regraExpressao(json);
        assertEquals("x + y", recuperada.getExpressao());
        assertEquals("soma", recuperada.getVariavel());
        assertNull(recuperada.getDescricao());
        assertEquals(Float.MAX_VALUE, recuperada.getValorMaximo(), 0.0001d);
        assertEquals(Float.MIN_VALUE, recuperada.getValorMinimo(), 0.0001d);
    }

    private void verificaRegraExpressao(RegraExpressao expressao) {
        String json = sz.toJson(expressao);
        Regra recuperada = sz.regraExpressao(json);

        assertEquals(expressao, recuperada);
    }

    @Test
    public void serializarConfiguracao() {
        List<Regra> n = new ArrayList<>();
        n.add(getRegraExpressao());
        //n.add(getRegraPontosPorRelato());

        Configuracao c = new Configuracao("c", "d", "detalhes", new Date(), n);

        String json = sz.toJson(c);

        System.out.println(json);

        Configuracao recuperada = sz.configuracao(json);

        assertEquals(json, sz.toJson(recuperada));
    }

    @Test
    public void serializarRelato() {
        Map<String, Valor> atributos = new HashMap<>();
        atributos.put("idade", new Valor(49f));
        atributos.put("aposentado", new Valor(false));

        Relato r = new Relato("classe", atributos);

        String json = sz.toJson(r);
        Relato recuperado = sz.relato(json);

        assertEquals(json, sz.toJson(recuperado));
    }

    @Test
    public void serializarRelatorio() {
        Map<String, Valor> atributos = new HashMap<>();
        atributos.put("peso", new Valor(49f));

        Relato r1 = new Relato("r1", atributos);
        Relato r2 = new Relato("r2", atributos);

        List<Relato> relatos = new ArrayList<>(2);
        relatos.add(r1);
        relatos.add(r2);
        Relatorio relatorio = new Relatorio("relatorio", 2016, relatos);

        String json = sz.toJson(relatorio);

        Relatorio recuperado = sz.relatorio(json);

        assertEquals(json, sz.toJson(recuperado));
    }
}
