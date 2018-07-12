package com.morganoliveira.contabills.br;

import com.activeandroid.Model;
import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.dao.OperacaoDAO;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Operacao;
import com.morganoliveira.contabills.model.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Morgan Oliveira on 02/04/2018.
 */

public class OperacaoBR extends DefaultBR<Operacao, OperacaoDAO> {
    private String DataI;
    private OperacaoDAO dao1;
    public OperacaoBR(OperacaoDAO dao) {
        super(dao);
        this.dao1 = dao;
    }

    @Override
    protected void validateSave(Operacao operacao) throws SaveException {

    }

    @Override
    protected void validateDelete(Operacao operacao) throws DeleteException {

    }



    public List<Operacao> selectDebitos(Usuario user, String mes, String ano) {

        List<Operacao> debitos = dao1.selectDebitos("tipo = 'Debitar' and status = 'Efetivado' and Usuario = '"+String.valueOf(user.getId())+"'");
        for(int k = 0; k < debitos.size(); k++) {
            String[] data = debitos.get(k).getData_final().split("/");
            if (data[1].equals(mes) == false) {
                debitos.remove(debitos.get(k));
            }else if(data[2].equals(ano) == false){
                debitos.remove(debitos.get(k));
            }
        }
        return debitos;
    }

    public ArrayList<String> DebitosPorCategoria(String mes, String ano) {

        List<Operacao> debt = Facade.getmInstance().getDebitosMes(Facade.getmInstance().getUsuarioLogado(), mes, ano);

        List<Categoria> categorias = Facade.getmInstance().selectCategorias(Facade.getmInstance().getUsuarioLogado());
        ArrayList<String> CategDeb = new ArrayList<>();



        int k = 0, j, w, y;

        float somaDeb = 0;
        boolean CategSemDebito = true;

        for(y = 0; y < categorias.size(); y++ ){

            for (w = 0; w < debt.size(); w++){
                if(categorias.get(y).getDescricao().equals(debt.get(w).getCategoria().getDescricao())) {
                    CategSemDebito = false;
                    somaDeb += debt.get(w).getValor();

                }
            }
            if(CategSemDebito == false){
                CategDeb.add(categorias.get(y).getDescricao());
                CategDeb.add(String.valueOf(somaDeb));
                CategSemDebito = true;
                somaDeb = 0;
            }
        }

        return CategDeb;

    }

    public float getTotalDebitos(String mes, String ano) {
        List<Operacao> debt = Facade.getmInstance().getDebitos(Facade.getmInstance().getUsuarioLogado(), mes, ano);
        float totalDebitos = 0;
        for (int j = 0; j < debt.size(); j++){
            totalDebitos += debt.get(j).getValor();
        }
        return totalDebitos;
    }
    private void criaStringData(int ano, int mes, int dia) {
        DataI = (dia < 10 ? "0"+dia : dia)+"/"+(mes+1 < 10 ? "0"+(mes+1) : (mes+1))+"/"+ano;
    }


    public void RealizaCredDeb(String tipo2, Conta conta, Categoria categoria, double valorCred, String descricaoCredi, String dti, String dtf, int repete) {

        Calendar calend = Calendar.getInstance();
        criaStringData(calend.get(Calendar.YEAR), calend.get(Calendar.MONTH), calend.get(Calendar.DAY_OF_MONTH));
        String status;
        String[] dataI = dti.split("/");
        String [] dataHJ = DataI.split("/");
        Conta novaC;

        if(dataI[0].equals(dataHJ[0]) && dataI[1].equals(dataHJ[1]) && dataI[2].equals(dataHJ[2])){
            status = "Efetivado";

            if(tipo2.equals("Creditar")){
                conta.setSaldo((conta.getSaldo() + valorCred));
            }else{
                conta.setSaldo((conta.getSaldo() - valorCred));
            }

        }else if (dti.equals("Não Pago")){
            status = "Pendente";
        }else{
            status = "Efetivado";
        }

        novaC = conta;

        try {
            Facade.getmInstance().saveConta(novaC);

        } catch (SaveException e) {
            e.printStackTrace();
        }

        Operacao opera = new Operacao(status, Facade.getmInstance().getUsuarioLogado(), valorCred ,descricaoCredi,categoria, dti, dtf,repete,novaC,tipo2 );


        try {
            Facade.getmInstance().saveOperacao(opera);
        } catch (SaveException e) {
            e.printStackTrace();
        }
    }

    public List<Operacao> selectCreditos(Usuario user, String mes, String ano) {
        List<Operacao> creditos = dao1.selectCreditos("tipo = 'Creditar' and status = 'Efetivado' and Usuario = '"+String.valueOf(user.getId())+"'");
        for(int k = 0; k < creditos.size(); k++) {
            String[] data = creditos.get(k).getData_final().split("/");
            if (data[1].equals(mes) == false) {
                creditos.remove(creditos.get(k));
            }else if(data[2].equals(ano) == false){
                creditos.remove(creditos.get(k));
            }
        }
        return creditos;

    }

    public List<Operacao> selectCreditosMes(Usuario user, String mes, String ano) {
        List<Operacao> creditosMes = new ArrayList<>();
        List<Operacao> creditos =  dao1.selectCreditos("tipo = 'Creditar' and status = 'Efetivado' and Usuario = '"+String.valueOf(user.getId())+"'");
        for(int k = 0; k < creditos.size(); k++){
            String [] data = creditos.get(k).getData_final().split("/");
            if(data[1].equals(mes) && data[2].equals(ano)){
                creditosMes.add(creditos.get(k));
            }


        }
        return creditosMes;
    }

    public List<Operacao> selectDebitosMes(Usuario user, String mes, String ano) {
        List<Operacao> debitosMes = new ArrayList<>();
        List<Operacao> debitos = dao1.selectDebitos("tipo = 'Debitar' and status = 'Efetivado' and Usuario = '"+String.valueOf(user.getId())+"'");
        for(int k = 0; k < debitos.size(); k++){
            String [] data = debitos.get(k).getData_final().split("/");
            if(data[1].equals(mes) && data[2].equals(ano)){
                debitosMes.add(debitos.get(k));
            }


        }
        return debitosMes;
    }

    public float getTotalCreditos(String mes, String ano) {
        List<Operacao> cred = Facade.getmInstance().getCreditos(Facade.getmInstance().getUsuarioLogado(), mes, ano);
        float totalCreditos = 0;
        for (int j = 0; j < cred.size(); j++){
            totalCreditos += cred.get(j).getValor();
        }
        return totalCreditos;
    }

    public ArrayList<String> CreditosPorCategoria(String mes, String ano) {

        Calendar calendario = Calendar.getInstance();
        List<Operacao> cred = Facade.getmInstance().getCreditosMes(Facade.getmInstance().getUsuarioLogado(), mes, ano);

        List<Categoria> categorias = Facade.getmInstance().selectCategorias(Facade.getmInstance().getUsuarioLogado());
        ArrayList<String> CategDeb = new ArrayList<>();

        int k = 0, j, w, y;

        float somaCred = 0;
        boolean CategSemCred = true;

        for(y = 0; y < categorias.size(); y++ ){

            for (w = 0; w < cred.size(); w++){
                if(categorias.get(y).getDescricao().equals(cred.get(w).getCategoria().getDescricao())) {
                    CategSemCred = false;
                    somaCred += cred.get(w).getValor();

                }
            }
            if(CategSemCred == false){
                CategDeb.add(categorias.get(y).getDescricao());
                CategDeb.add(String.valueOf(somaCred));
                CategSemCred = true;
                somaCred = 0;
            }
        }

        return CategDeb;


    }

    public List<Operacao> selectDebitosFuturos(Usuario user) {
        List<Operacao> debitos = new ArrayList<>();
        List<Operacao> debitosF = dao1.selectDebitos("tipo = 'Debitar' and status = 'Pendente' and Usuario = '"+String.valueOf(user.getId())+"'");
        Calendar hoje = Calendar.getInstance();
        Calendar dataOpe = Calendar.getInstance();

        for(int k = 0; k < debitosF.size(); k++){
            String [] data = debitosF.get(k).getData_final().split("/");
            dataOpe.set(Integer.valueOf(data[2]), Integer.valueOf(data[1])-1, Integer.valueOf(data[0]));
            if(hoje.before(dataOpe)){
               debitos.add(debitosF.get(k));
            }

        }

        return debitos;
    }

    public List<Operacao> selectCreditosFuturos(Usuario user) {
        List<Operacao> creditosF = dao1.selectCreditos("tipo = 'Creditar' and status = 'Pendente' and Usuario = '"+String.valueOf(user.getId())+"'");
        List<Operacao> cred = new ArrayList<>();
        Calendar hoje = Calendar.getInstance();
        Calendar dataOpe = Calendar.getInstance();

        for(int k = 0; k < creditosF.size(); k++){
            String [] data = creditosF.get(k).getData_final().split("/");
            dataOpe.set(Integer.valueOf(data[2]), Integer.valueOf(data[1])-1, Integer.valueOf(data[0]));
            if(hoje.before(dataOpe)){
                cred.add(creditosF.get(k));
            }

        }
        return cred;
    }

    public List<Operacao> selectContasAtrasadas(Usuario user) {

        List<Operacao> creditosF = dao1.selectCreditos("tipo = 'Creditar' and status = 'Pendente' and Usuario = '"+String.valueOf(user.getId())+"'");
        List<Operacao> debitosF = dao1.selectDebitos("tipo = 'Debitar' and status = 'Pendente' and Usuario = '"+String.valueOf(user.getId())+"'");
        List<Operacao> atrasadas = new ArrayList<>();
        Calendar hoje = Calendar.getInstance();
        Calendar dataOpe = Calendar.getInstance();

        for(int k = 0; k < debitosF.size(); k++){
            String [] data = debitosF.get(k).getData_final().split("/");
            dataOpe.set(Integer.valueOf(data[2]), Integer.valueOf(data[1])-1, Integer.valueOf(data[0]));
            if(hoje.after(dataOpe)){
                atrasadas.add(debitosF.get(k));
            }else if(hoje.equals(dataOpe)){
                atrasadas.add(debitosF.get(k));
            }

        }

        for(int k = 0; k < creditosF.size(); k++){
            String [] data = creditosF.get(k).getData_final().split("/");
            dataOpe.set(Integer.valueOf(data[2]), Integer.valueOf(data[1])-1, Integer.valueOf(data[0]));
            if(hoje.after(dataOpe)){
                atrasadas.add(creditosF.get(k));
            }

        }
        return atrasadas;
    }

    public void RealizaOperacao(Operacao operacao) {
        Conta conta = Facade.getmInstance().selectConta(operacao.getConta().getDescricao(), Facade.getmInstance().getUsuarioLogado());
        operacao.setStatus("Efetivado");
        Calendar calend = Calendar.getInstance();
        criaStringData(calend.get(Calendar.YEAR), calend.get(Calendar.MONTH), calend.get(Calendar.DAY_OF_MONTH));
        operacao.setData_inicial(DataI);

        if(operacao.getTipo().equals("Creditar")){
            conta.setSaldo((conta.getSaldo() + operacao.getValor()));
        }else{
            conta.setSaldo((conta.getSaldo() - operacao.getValor()));
        }

        Conta nova = conta;

        try {
            Facade.getmInstance().saveConta(nova);

        } catch (SaveException e) {
            e.printStackTrace();
        }

        Operacao p = operacao;

        try {
            Facade.getmInstance().saveOperacao(p);
        } catch (SaveException e) {
            e.printStackTrace();
        }


    }
}
