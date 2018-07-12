package com.morganoliveira.contabills.model;

import android.graphics.Bitmap;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by andre on 15/03/2018.
 */
@Table(name = "Categoria")
public class Categoria extends Model {



    @Column(name = "descricao")
    private String descricao;
    @Column(name = "Usuario")
    private Usuario usuario;


    public Categoria() {
    }

    public Categoria(String descricao, Usuario user) {
        this.usuario = user;
        this.descricao = descricao;

    }



    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
