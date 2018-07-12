package com.morganoliveira.contabills.controller;

import com.morganoliveira.contabills.br.CategoriaBR;
import com.morganoliveira.contabills.br.ContaBR;
import com.morganoliveira.contabills.br.OperacaoBR;
import com.morganoliveira.contabills.br.UsuarioBR;
import com.morganoliveira.contabills.dao.CategoriaDAO;
import com.morganoliveira.contabills.dao.ContaDAO;
import com.morganoliveira.contabills.dao.OperacaoDAO;
import com.morganoliveira.contabills.dao.UsuarioDAO;
import com.morganoliveira.contabills.exceptions.CategoriaExistente;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.exceptions.UsuarioExistente;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Operacao;
import com.morganoliveira.contabills.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Morgan Oliveira on 22/03/2018.
 */

public class Facade {
    private static Facade mInstance;

    public static Facade getmInstance() {
        if (mInstance == null) {
            mInstance = new Facade();

        }
        return mInstance;
    }

    private UsuarioBR userBR;
    private ContaBR contaBR;
    private OperacaoBR creditoBR;
    private CategoriaBR categoriaBR;


    private Facade() {
        UsuarioDAO userDAO = new UsuarioDAO();
        this.userBR = new UsuarioBR(userDAO);

        ContaDAO contaDAO = new ContaDAO();
        this.contaBR = new ContaBR(contaDAO);

        OperacaoDAO creditoDAO = new OperacaoDAO();
        this.creditoBR = new OperacaoBR(creditoDAO);

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        this.categoriaBR = new CategoriaBR(categoriaDAO);
    }

    public boolean existUser(String user, String senha)  {
        return userBR.exists(user, senha);
    }

    public Usuario getUsuario(String user, String senha){
        return userBR.selectUsuario(user, senha);
    }

    public Usuario getUsuarioLogado(){
        return userBR.selectUserLogado();
    }

    public void saveUsuario(Usuario user) throws SaveException {
        try {
            userBR.save(user);
        } catch (SaveException s) {
            throw new SaveException(s.getMessage());
        }

    }

    public void saveConta(Conta conta) throws SaveException {
        try {
            contaBR.save(conta);
        } catch (SaveException s) {
            throw new SaveException(s.getMessage());
        }

    }

    public void saveOperacao(Operacao cred) throws SaveException{

        try {
            creditoBR.save(cred);
        } catch (SaveException e) {
            e.printStackTrace();
        }

    }




    public Conta selectConta(String nome, Usuario user) {
        return contaBR.selectConta(nome, user);
    }

    public List<Conta> selectContas(Usuario user) {
        return contaBR.selectContas(user);
    }


    public void deletConta(Conta conta)  throws DeleteException  {
        try {
            contaBR.delete(conta);
        } catch (DeleteException e) {
            throw new DeleteException(e.getMessage());
        }
    }

    public void saveCategoria(String categoria, Usuario user) throws SaveException {
        Categoria c = new Categoria(categoria, user);
        categoriaBR.save(c);

    }

    public List<Categoria> selectCategorias(Usuario user) {
       return categoriaBR.selectCategorias(user);
    }

    public Categoria selectCategoria(String categoria, Usuario user) {
        return categoriaBR.selectCategoria(categoria, user);
    }

    public List<Operacao> getDebitos(Usuario user, String mes, String ano) {
        return creditoBR.selectDebitos(user, mes, ano);
    }

    public double calculaSaldoTotal(List<Conta> c) {
        return contaBR.calculaSaldoTotal(c);
    }

    public ArrayList<String> DebitosPorCategoria(String mes, String ano) {
        return creditoBR.DebitosPorCategoria(mes, ano);
    }

    public float getTotalDebitos(String mes, String ano) {
        return creditoBR.getTotalDebitos(mes, ano);
    }

    public void RealizaCreDeb(String tipo2, Conta conta, Categoria categoria, double valorCred, String descricaoCredi, String dti, String dtf, int repete) {
        creditoBR.RealizaCredDeb(tipo2, conta, categoria, valorCred, descricaoCredi, dti, dtf, repete);
    }

    public List<Operacao> getCreditos(Usuario user, String mes, String ano) {
        return creditoBR.selectCreditos(user, mes, ano);
    }

    public List<Operacao> getCreditosMes(Usuario user, String mes, String ano) {
        return creditoBR.selectCreditosMes(user, mes, ano);
    }

    public List<Operacao> getDebitosMes(Usuario user, String mes, String ano) {
        return creditoBR.selectDebitosMes(user, mes, ano);
    }

    public float getTotalCreditos(String mes, String ano) {
        return creditoBR.getTotalCreditos(mes, ano);
    }

    public ArrayList<String> CreditosPorCategoria(String mes, String ano) {
        return creditoBR.CreditosPorCategoria(mes, ano);

    }


    public List<Operacao> getDebitosFuturos(Usuario user) {
        return creditoBR.selectDebitosFuturos(user);
    }

    public List<Operacao> getCreditosFuturos(Usuario user) {
        return  creditoBR.selectCreditosFuturos(user);
    }

    public List<Operacao> getContasAtrasadas(Usuario user) {
        return creditoBR.selectContasAtrasadas(user);


    }

    public void RealizaOperacao(Operacao operacao) {
        creditoBR.RealizaOperacao(operacao);
    }

    public boolean existCategoria(String s, Usuario usuarioLogado){
        return categoriaBR.existCategoria(s, usuarioLogado);
    }
}
