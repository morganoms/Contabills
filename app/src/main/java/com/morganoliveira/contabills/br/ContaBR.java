package com.morganoliveira.contabills.br;

import com.activeandroid.Model;
import com.morganoliveira.contabills.dao.ContaDAO;
import com.morganoliveira.contabills.dao.DefaultDAO;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Usuario;

import java.util.List;

/**
 * Created by Morgan Oliveira on 28/03/2018.
 */

public class ContaBR extends DefaultBR<Conta, ContaDAO> {

    private ContaDAO dao1;

    public ContaBR(ContaDAO dao1) {
        super(dao1);
        this.dao1 = dao1;
    }

    public Conta selectConta(String nome, Usuario user){
        return dao1.selectSingle("descricao = '"+nome+"' and Usuario = '"+String.valueOf(user.getId())+"'");
    }

    public List<Conta> selectContas(Usuario user){
        return dao1.selectContasWhere("Usuario = '"+String.valueOf(user.getId())+"'");
    }


    @Override
    protected void validateSave(Conta conta) throws SaveException {

    }

    @Override
    protected void validateDelete(Conta conta) throws DeleteException {

    }

    public double calculaSaldoTotal(List<Conta> c) {
        double soma = 0;
        int in;
        for(in = 0; in < c.size();in++){
            soma = soma + c.get(in).getSaldo();
        }
        return soma;
    }
}
