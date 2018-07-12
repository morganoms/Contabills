package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.exceptions.CategoriaExistente;
import com.morganoliveira.contabills.exceptions.SaveException;

public class ActivityCategoria extends AppCompatActivity {

    private EditText descricao;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Bundle dado = i.getExtras();
        tipo = dado.getString("tipoOperacao").toString();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descricao = findViewById(R.id.descCateg);
                if(descricao.getText().toString().isEmpty()){
                    Toast.makeText(ActivityCategoria.this, getString(R.string.desciVazio), Toast.LENGTH_SHORT).show();
                }else if(Facade.getmInstance().existCategoria(descricao.getText().toString(), Facade.getmInstance().getUsuarioLogado())){
                        Toast.makeText(ActivityCategoria.this, getString(R.string.categCadastrada), Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            Facade.getmInstance().saveCategoria(descricao.getText().toString(), Facade.getmInstance().getUsuarioLogado());

                        } catch (SaveException e) {
                            e.printStackTrace();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("tipoOperacao", tipo);
                        Intent i = new Intent(ActivityCategoria.this, ActivityOperacao.class);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();

                    }


            }
        });
    }

}
