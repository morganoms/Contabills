package com.morganoliveira.contabills.dao;
import com.activeandroid.query.Select;
import com.morganoliveira.contabills.model.Usuario;
/**
 * Created by andre on 15/03/2018.
 */

public class UsuarioDAO extends DefaultDAO<Usuario>{

    public UsuarioDAO() {

        super(Usuario.class);



    }

    public boolean exists(String user, String senha) {
        return new Select().from(Usuario.class).where("username = '"+user+"' ").and("senha = '"+senha+"' ").exists();
    }

}
