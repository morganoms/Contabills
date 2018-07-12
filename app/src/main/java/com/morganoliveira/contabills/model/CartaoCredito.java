package com.morganoliveira.contabills.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by andre on 15/03/2018.
 */
@Table(name="CartaoCredito")
public class CartaoCredito extends Conta{

    @Column(name="dataVencimento")
    private Date data_vencimento;
    @Column(name="dataFechamento")
    private Date data_fechamento;
    @Column(name="limite")
    private double limite_credito;

    public CartaoCredito(Usuario usuario, double saldo, String descricao, Date data_vencimento, Date data_fechamento, double limite_credito) {
        super(usuario, saldo, descricao);
        this.data_vencimento = data_vencimento;
        this.data_fechamento = data_fechamento;
        this.limite_credito = limite_credito;
    }

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Date getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(Date data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public double getLimite_credito() {
        return limite_credito;
    }

    public void setLimite_credito(double limite_credito) {
        this.limite_credito = limite_credito;
    }
}
