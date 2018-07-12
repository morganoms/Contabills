package com.morganoliveira.contabills.br;

import com.morganoliveira.contabills.dao.UsuarioDAO;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.exceptions.UsuarioExistente;
import com.morganoliveira.contabills.model.Usuario;

/**
 * Created by Morgan Oliveira on 22/03/2018.
 */

public class UsuarioBR extends DefaultBR<Usuario, UsuarioDAO>{

    private UsuarioDAO dao1;

    public UsuarioBR(UsuarioDAO dao) {
        super(dao);
        dao1 = dao;
    }


    public boolean exists(String user, String senha)  {
        return dao1.exists(user, senha);

    }

    public Usuario selectUsuario(String user, String senha){
        return dao1.selectSingle("username = '"+user+"' and senha = '"+senha+"'");
    }


    @Override
    protected void validateSave(Usuario usuario) throws SaveException {
        if(usuario.getNome().isEmpty() || usuario.getSobrenome().isEmpty() || usuario.getUsername().isEmpty()) {
            throw new SaveException("ERROR!");
        }
    }

    @Override
    protected void validateDelete(Usuario usuario) throws DeleteException {

    }

    public Usuario selectUserLogado() {

        return dao1.selectSingle("logado = '"+1+"'");
    }
}
