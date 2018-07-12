package com.morganoliveira.contabills.dao;


import com.morganoliveira.contabills.model.Operacao;

import java.util.List;
import com.activeandroid.query.Select;

/**
 * Created by Morgan Oliveira on 02/04/2018.
 */

public class OperacaoDAO extends DefaultDAO<Operacao> {

    public OperacaoDAO() {
        super(Operacao.class);
    }

    public List<Operacao> selectDebitos(String clause) {
        return new Select().from(Operacao.class).where(clause).execute();
    }

    public List<Operacao> selectCreditos(String clause) {
        return new Select().from(Operacao.class).where(clause).execute();
    }
}
