package com.morganoliveira.contabills.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by andre on 15/03/2018.
 */
@Table(name = "Usuario")
public class Usuario extends Model {

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "username")
    private String username;

    @Column(name = "senha")
    private String senha;

    @Column(name = "logado")
    private int logado;

    public Usuario() {
    }

    public Usuario(String nome, String sobrenome, String username, String senha, int logado) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.username = username;
        this.senha = senha;
        this.logado = logado;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public int getLogado() {
        return logado;
    }

    public void setLogado(int logado) {
        this.logado = logado;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Conta> getConta() {return getMany(Conta.class, "Usuario");}

}
