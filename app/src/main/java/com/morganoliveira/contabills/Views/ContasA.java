package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.model.Operacao;

public class ContasA extends AppCompatActivity {
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contasa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Bundle dado = i.getExtras();
        titulo = dado.getString("titulo");
        getSupportActionBar().setTitle(titulo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        popularListView();


    }

    private void popularListView() {
        if(titulo.equals(getString(R.string.Capagar))) {
            ListView lv = findViewById(R.id.listCont);
            ArrayAdapter<Operacao> adapter = new OperacaoPorContasAdapter(this, Facade.getmInstance().getDebitosFuturos(Facade.getmInstance().getUsuarioLogado()), titulo);
            lv.setAdapter(adapter);
        }else if(titulo.equals(getString(R.string.Creceber))){
            ListView lv = findViewById(R.id.listCont);
            ArrayAdapter<Operacao> adapter = new OperacaoPorContasAdapter(this, Facade.getmInstance().getCreditosFuturos(Facade.getmInstance().getUsuarioLogado()), titulo);
            lv.setAdapter(adapter);
        }else{
            ListView lv = findViewById(R.id.listCont);
            ArrayAdapter<Operacao> adapter = new OperacaoPorContasAdapter(this, Facade.getmInstance().getContasAtrasadas(Facade.getmInstance().getUsuarioLogado()), titulo);
            lv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent it = new Intent(ContasA.this, MainActivity.class);
            startActivity(it);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
