package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Usuario;

public class CadastroConta extends AppCompatActivity {

    private FloatingActionButton fab1;
    private TextView desc, valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desc = findViewById(R.id.ContaDes);
        valor = findViewById(R.id.ContaVLR);

        fab1 = findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveConta();
                Intent it = new Intent(CadastroConta.this, MainActivity.class);
                startActivity(it);

            }
        });


    }

    private void saveConta() {
        if(valor.getText().toString().isEmpty() || desc.getText().toString().isEmpty()) {
            Toast.makeText(this, getText(R.string.preencher), Toast.LENGTH_SHORT).show();
        }else if (Facade.getmInstance().selectConta(desc.getText().toString(), Facade.getmInstance().getUsuarioLogado()) != null) {
            Toast.makeText(this, getText(R.string.ContaCadstrada), Toast.LENGTH_SHORT).show();
        }else{
            Usuario user = Facade.getmInstance().getUsuarioLogado();
            Conta conta = new Conta(user, Double.parseDouble(valor.getText().toString()), desc.getText().toString());
            try {
                Facade.getmInstance().saveConta(conta);
            } catch (SaveException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
