package com.morganoliveira.contabills.dao;
import com.activeandroid.query.Select;
import com.morganoliveira.contabills.model.Conta;

import java.util.List;

/**
 * Created by andre on 15/03/2018.
 */

public class ContaDAO extends DefaultDAO<Conta>{

    public ContaDAO() {
        super(Conta.class);
    }

    public List<Conta> selectContasWhere(String clause) {
        return new Select().from(Conta.class).where(clause).execute();
    }
}
