package com.morganoliveira.contabills.dao;
import com.activeandroid.query.Select;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Usuario;

import java.util.List;

/**
 * Created by andre on 15/03/2018.
 */

public class CategoriaDAO extends DefaultDAO<Categoria>{

    public CategoriaDAO() {
        super(Categoria.class);
    }

    public List<Categoria> selectCategorias(String clause) {
        return new Select().from(Categoria.class).where(clause).execute();
    }
}
