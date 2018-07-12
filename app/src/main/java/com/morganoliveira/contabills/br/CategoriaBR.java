package com.morganoliveira.contabills.br;

import com.activeandroid.Model;
import com.morganoliveira.contabills.dao.CategoriaDAO;
import com.morganoliveira.contabills.dao.ContaDAO;
import com.morganoliveira.contabills.dao.DefaultDAO;
import com.morganoliveira.contabills.exceptions.CategoriaExistente;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Usuario;

import java.util.List;

/**
 * Created by Morgan Oliveira on 03/04/2018.
 */

    public class CategoriaBR extends DefaultBR<Categoria, CategoriaDAO> {
        private CategoriaDAO dao1;

    public CategoriaBR(CategoriaDAO dao) {
        super(dao);
        dao1 = dao;
    }

    @Override
    protected void validateSave(Categoria model) throws SaveException {
    }

    @Override
    protected void validateDelete(Categoria model) throws DeleteException {

    }

    public List<Categoria> selectCategorias(Usuario user) {
        return dao1.selectCategorias("Usuario = '"+String.valueOf(user.getId())+"'");
    }


    public Categoria selectCategoria(String categoria, Usuario user) {
        return dao1.selectSingle("descricao = '"+categoria+"' and  Usuario = '"+String.valueOf(user.getId())+"'");
    }

    public boolean existCategoria(String s, Usuario usuarioLogado)  {
        if((dao1.selectSingle("descricao = '"+s+"' and  Usuario = '"+String.valueOf(usuarioLogado.getId())+"'")) != null){
            return true;
        }else{
            return false;
        }
    }
}
