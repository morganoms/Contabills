package com.morganoliveira.contabills.Views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Operacao;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ActivityOperacao extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener{

    private EditText valorCred, descricaoCredi;
    private CheckBox repetir;
    private TextView tx,datini, dtfim;
    private Date dti, dtf;
    private int repete = 0, ano, mes, dia;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private FloatingActionButton fab;
    private Spinner sp1, sp2;
    private String tipo, contaSelecionada, categoriaSelecionada, DataI;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getComponentes();

        dtfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                chamaDateDialog();
            }
        });

        datini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                chamaDateDialog();
            }
        });

        //Titulo Activity
        Intent i = getIntent();
        Bundle dado = i.getExtras();
        tipo = dado.getString("tipoOperacao").toString();
        getSupportActionBar().setTitle(tipo);



        carregarSpinner1();
        carregarSpinner2();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComponentes();

                if(valorCred.getText().toString().isEmpty()  ||  dtfim.getText().toString().isEmpty()) {

                    Toast.makeText(ActivityOperacao.this,getText(R.string.preencher) , Toast.LENGTH_SHORT).show();

                }else{


                        Conta conta =  Facade.getmInstance().selectConta(contaSelecionada, Facade.getmInstance().getUsuarioLogado());
                        Categoria categoria = Facade.getmInstance().selectCategoria(categoriaSelecionada, Facade.getmInstance().getUsuarioLogado());

                        if(conta == null || categoria == null){
                            Toast.makeText(ActivityOperacao.this,getText(R.string.selecioneUmaConta) , Toast.LENGTH_SHORT).show();
                        }else{

                            Intent i = getIntent();
                            Bundle dado = i.getExtras();
                            String tipo2 = dado.getString("tipoOperacao").toString();
                            if(datini.getText().toString().equals(getString(R.string.dataIni))){
                                Facade.getmInstance().RealizaCreDeb(tipo2, conta, categoria, Double.parseDouble(valorCred.getText().toString()), descricaoCredi.getText().toString(), "Não Pago", dtfim.getText().toString(), repete);
                            }else {
                                Facade.getmInstance().RealizaCreDeb(tipo2, conta, categoria, Double.parseDouble(valorCred.getText().toString()), descricaoCredi.getText().toString(), datini.getText().toString(), dtfim.getText().toString(), repete);
                            }
                            Intent it = new Intent(ActivityOperacao.this, MainActivity.class);
                            startActivity(it);
                        }



                }

            }
        });

    }

    private void chamaDateDialog() {
        initData();
        Calendar cDeafault = Calendar.getInstance();
        cDeafault.set(ano, mes, dia);


        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dateDialog =  com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                cDeafault.get(Calendar.YEAR),
                cDeafault.get(Calendar.MONTH),
                cDeafault.get(Calendar.DAY_OF_MONTH)
        );

        Calendar cMin = Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();
        cMax.set(cMax.get(Calendar.YEAR), 11, 31);
        cMin.set(2000, 0, 1);
        dateDialog.setMinDate(cMin);
        dateDialog.setMaxDate(cMax);

        List<Calendar> listaDatas = new LinkedList<>();
        Calendar [] daysArray;
        Calendar cAux = Calendar.getInstance();

        while(cMin.getTimeInMillis() <= cMax.getTimeInMillis()){

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(cMin.getTimeInMillis());
            listaDatas.add(c);

            cMin.setTimeInMillis(cMin.getTimeInMillis() + (24 * 60 * 60 * 1000));
        }

        daysArray = new Calendar[listaDatas.size()];
        for (int i = 0; i < daysArray.length; i++){
            daysArray[i] = listaDatas.get(i);

        }
        dateDialog.setSelectableDays(daysArray);
        dateDialog.setOnCancelListener(this);
        dateDialog.show(getFragmentManager(), "DatePickerDialog");



    }

    public void initData(){
        if(ano == 0){
            Calendar calendario = Calendar.getInstance();
            ano = calendario.get(Calendar.YEAR);
            mes = calendario.get(Calendar.MONTH);
            dia = calendario.get(Calendar.DAY_OF_MONTH);

        }
    }

    private void carregarSpinner2() {

        List<Categoria> categ = Facade.getmInstance().selectCategorias(Facade.getmInstance().getUsuarioLogado());
        List<String> descricao = new ArrayList<String>();
        descricao.add(getString(R.string.categ));
        sp2 = findViewById(R.id.spinner2);
        if(categ.isEmpty() == false){
            for(int k = 0; k < categ.size(); k++ ){
                descricao.add(categ.get(k).getDescricao());
            }
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, descricao);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp2.setAdapter(adapter);
            sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    categoriaSelecionada = adapterView.getItemAtPosition(pos).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            Toast.makeText(this, getText(R.string.categaEmpty), Toast.LENGTH_SHORT).show();
        }


    }

    private void carregarSpinner1() {
        List<Conta> c = Facade.getmInstance().selectContas(Facade.getmInstance().getUsuarioLogado());
        List<String> descricao = new ArrayList<String>();

        if(tipo.equals("Creditar")){ descricao.add(getString(R.string.contaCred)); }
        else{descricao.add(getString(R.string.contaDeb));}

        if(c.isEmpty() == false) {
            for (int k = 0; k < c.size(); k++) {
                descricao.add(c.get(k).getDescricao());
            }
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, descricao);
            sp1 = findViewById(R.id.contasTeste);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp1.setAdapter(adapter);
            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    contaSelecionada = adapterView.getItemAtPosition(pos).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            Toast.makeText(this, getText(R.string.contaEmpty), Toast.LENGTH_SHORT).show();
        }
    }

    public void getComponentes(){

        valorCred = findViewById(R.id.valorCred);

        datini = findViewById(R.id.dtini2);
        dtfim = findViewById(R.id.dtfim);
        repetir = findViewById(R.id.repete);
        descricaoCredi = findViewById(R.id.descricaoCred);
        sp1 = findViewById(R.id.contasTeste);
        sp2 = findViewById(R.id.spinner2);
        if(repetir.isChecked()){
            repete = 1;
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_categoria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }else if(id == R.id.cadastroCateg){
            Bundle bundle = new Bundle();
            bundle.putString("tipoOperacao", tipo);
            Intent it = new Intent(ActivityOperacao.this, ActivityCategoria.class);
            it.putExtras(bundle);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int ano, int mes, int dia) {
        criaStringData(ano, mes, dia);
        if(flag) {
            dtfim.setText(DataI);
        }else{
            datini.setText(DataI);
        }

    }

    private void criaStringData(int ano, int mes, int dia) {
        DataI = (dia < 10 ? "0"+dia : dia)+"/"+(mes+1 < 10 ? "0"+(mes+1) : (mes+1))+"/"+ano;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        ano = mes = dia = 0;
        if(datini.getText().toString().equals(getText(R.string.dataIni))) {
            dtfim.setText(getText(R.string.dataFim));
        }else {
            datini.setText(getString(R.string.dataIni));
        }

    }
}

