package com.morganoliveira.contabills.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "Operacao")
public class Operacao extends Model {


    @Column(name = "valor")
    private double valor;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "categoeia")
    private Categoria categoria;
    @Column(name = "dtInicial")
    private String data_inicial;
    @Column(name = "dtFinal")
    private String data_final;
    @Column(name = "repeticao")
    private int repeticao;
    @Column(name = "Conta")
    private Conta conta;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "Usuario")
    private Usuario usuario;
    @Column(name = "status")
    private String status;




    public Operacao() {
    }




    public Operacao(String status, Usuario user, double valor, String descricao, Categoria categoria, String data_inicial, String data_final, int repeticao, Conta conta, String tipo) {
        this.status = status;

        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.data_inicial = data_inicial;
        this.data_final = data_final;
        this.repeticao = repeticao;
        this.conta = conta;
        this.tipo = tipo;
        this.usuario = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(String data_inicial) {
        this.data_inicial = data_inicial;
    }

    public String getData_final() {
        return data_final;
    }

    public void setData_final(String data_final) {
        this.data_final = data_final;
    }

    public int getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(int repeticao) {
        this.repeticao = repeticao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
