package com.morganoliveira.contabills.Views;

/**
 * Created by Morgan Oliveira on 08/04/2018.
 */

public class ClasseAux {

    private String descricao;
    private double valor;

    public ClasseAux(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

