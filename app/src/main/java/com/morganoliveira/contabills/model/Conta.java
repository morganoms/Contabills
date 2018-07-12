package com.morganoliveira.contabills.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by andre on 15/03/2018.
 */
@Table(name = "Conta")
public class Conta extends Model {

    @Column(name = "Usuario")
    private Usuario usuario;
    @Column(name = "Saldo")
    private double saldo;
    @Column(name = "descricao")
    private String descricao;

    public Conta() {
    }

    public Conta(Usuario usuario, double saldo, String descricao) {
        this.usuario = usuario;
        this.saldo = saldo;
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Operacao> getOperacao() {return getMany(Operacao.class, "Conta");}
}
