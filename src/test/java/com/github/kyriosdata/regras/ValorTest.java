package com.github.kyriosdata.regras;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValorTest {

    @Test
    public void umValorLogicoFalso() {
        Valor verdadeiro = new Valor(false);
        assertFalse(verdadeiro.getBoolean());
    }

    @Test
    public void umValorLogicoVerdadeiro() {
        Valor verdadeiro = new Valor(true);
        assertTrue(verdadeiro.getBoolean());
    }

    @Test
    public void umValorString() {
        final String sequencia = "sequência de caracteres";
        Valor verdadeiro = new Valor(sequencia);
        assertEquals(sequencia, verdadeiro.getString());
    }

    @Test
    public void umValorLogicoObtidoDeTipoIncompativelResultaFalse() {
        Valor verdadeiro = new Valor("teste");
        assertFalse(verdadeiro.getBoolean());
    }

    @Test
    public void umValorLogicoObtidoDeTipoIncompativel2ResultaFalse() {
        Valor verdadeiro = new Valor(23);
        assertFalse(verdadeiro.getBoolean());
    }

    @Test
    public void umValorReal() {
        Valor pi = new Valor(3.14f);
        assertEquals(3.14, pi.getReal(), 0.0001f);
    }

    @Test
    public void umaSequenciaDeCaracteres() {
        Valor casa = new Valor("casa");
        assertEquals("casa", casa.getString());
    }

    @Test
    public void umaData() {
        Valor hoje = Valor.dataFromString("21/11/2008");

        assert hoje != null;
        LocalDate recuperada = hoje.getData();

        assertEquals(21, recuperada.getDayOfMonth());
        assertEquals(11, recuperada.getMonthValue());
        assertEquals(2008, recuperada.getYear());
    }

    @Test
    public void valorDiferenteDeOutroObjeto() {
        assertFalse(new Valor(1f).equals("ok"));
    }

    @Test
    public void hashCodeComparado() {
        Valor v1 = new Valor(1f);
        Valor v2 = new Valor(2f);
        assertEquals(v1.hashCode(), v1.hashCode());
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    public void umaDataInvalida() {
        assertNull(Valor.dataFromString("01/02"));
    }
}

