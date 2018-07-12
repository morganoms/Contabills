package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.Views.Dialog.AnoDialog;
import com.morganoliveira.contabills.Views.Fragmentos.FragmentoCredito;
import com.morganoliveira.contabills.Views.Fragmentos.FragmentoDebito;

import java.util.Calendar;

public class HistoricoMensal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AnoDialog.AnoDialogListener {

    public static String mes, ano;
    private String mesDescricao;
    private TextView tvano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView nav_menu = findViewById(R.id.nav_menu);
        nav_menu.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, new FragmentoCredito()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        Bundle dado = i.getExtras();
        mes = dado.getString("mes");
        mesDescricao = dado.getString("descricao");
        ano = dado.getString("ano");

        getSupportActionBar().setTitle(getString(R.string.title_activity_lista_conta)+" ("+mesDescricao+")");



        NavigationView nv = findViewById(R.id.nav_view2);
        View head = nv.getHeaderView(0);
        tvano = (TextView) head.findViewById(R.id.anoHeader);
        tvano.setText(ano);
        tvano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });



    }



    private void openDialog() {

        AnoDialog anoD = new AnoDialog();
        anoD.show(getSupportFragmentManager(), "ANO");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragmentoSelecionado = null;

            switch (item.getItemId()){
                case R.id.nav_cred:
                    fragmentoSelecionado = new FragmentoCredito();
                    break;
                case R.id.nav_deb:
                    fragmentoSelecionado = new FragmentoDebito();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain, fragmentoSelecionado).commit();

            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jan) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "01");
            bundle.putString("descricao", getString(R.string.janeiro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.fev) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "02");
            bundle.putString("descricao", getString(R.string.fevereiro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.mar) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "03");
            bundle.putString("descricao", getString(R.string.marco));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.abr) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "04");
            bundle.putString("descricao", getString(R.string.abril));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.mai) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "05");
            bundle.putString("descricao", getString(R.string.maio));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.jun) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "06");
            bundle.putString("descricao", getString(R.string.junho));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.jul) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "07");
            bundle.putString("descricao", getString(R.string.julho));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.ago) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "08");
            bundle.putString("descricao", getString(R.string.agosto));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.set) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "09");
            bundle.putString("descricao", getString(R.string.setembro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.out) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "10");
            bundle.putString("descricao", getString(R.string.outubro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.nov) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "11");
            bundle.putString("descricao", getString(R.string.novembro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }else if (id == R.id.dez) {
            Bundle bundle = new Bundle();
            bundle.putString("mes", "12");
            bundle.putString("descricao", getString(R.string.dezembro));
            bundle.putString("ano", ano);
            Intent it = new Intent(this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);
            this.finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_historico);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void applyText(String anoNovo) {

        NavigationView nv = findViewById(R.id.nav_view2);
        View head = nv.getHeaderView(0);
        TextView tv = (TextView) head.findViewById(R.id.anoHeader);
        tv.setText(anoNovo);
        ano = anoNovo;

    }
}
