package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.exceptions.UsuarioExistente;
import com.morganoliveira.contabills.model.Usuario;

public class Cadastro extends AppCompatActivity {

    private EditText nome;
    private EditText sobrenome;
    private EditText username;
    private EditText senha;
    private EditText ConfirmS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome=findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobreN);
        username = findViewById(R.id.nome4);
        senha = findViewById(R.id.senha);
        ConfirmS = findViewById(R.id.confirma);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (senha.getText().toString().isEmpty() || ConfirmS.getText().toString().isEmpty() || username.getText().toString().isEmpty()) {
                    Toast.makeText(Cadastro.this, "Preencha os Campos!", Toast.LENGTH_SHORT).show();

                } else if (Facade.getmInstance().existUser(username.getText().toString(), senha.getText().toString())) {
                        Toast.makeText(Cadastro.this, getString(R.string.userExiste), Toast.LENGTH_SHORT).show();
                } else if (senha.getText().toString().equals(ConfirmS.getText().toString())) {
                        save(view);
                        Toast.makeText(Cadastro.this, "Cadastrado", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Cadastro.this, LoginActivity.class);
                        startActivity(i);
                } else {
                        Toast.makeText(Cadastro.this, R.string.SenhaIncompativel, Toast.LENGTH_SHORT).show();
                }


        }

     });
    }

    public void save(View view){
        try {
            Usuario user = new Usuario(nome.getText().toString(), sobrenome.getText().toString(), username.getText().toString(), senha.getText().toString(), 0);
            Facade.getmInstance().saveUsuario(user);

        }catch (SaveException s){
           s.printStackTrace();
        }
    }

}
