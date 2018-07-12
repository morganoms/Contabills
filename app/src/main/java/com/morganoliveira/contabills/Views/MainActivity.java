package com.morganoliveira.contabills.Views;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Operacao;
import com.morganoliveira.contabills.model.Usuario;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab, fab2, fab3;
    private Animation fabOpen, fabClose, rotacaoF, rotacaoV;
    private TextView cred, debt, sal;
    boolean fabIsOpen = false;
    private Usuario user;
    private PieChart pieChat;
    double soma;
    private List<Conta> c;
    private NotificationManager notificationManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        user = Facade.getmInstance().getUsuarioLogado();

            if(user != null) {
                NavigationView nv = findViewById(R.id.nav_view);
                Menu head = nv.getMenu();
                MenuItem tv =  head.findItem(R.id.usuar);
                tv.setTitle(getString(R.string.usuario)+" "+user.getNome()+" "+user.getSobrenome());

            }

        calculaContas();

        NotificaContasAtrasadas();
        /*NotificationManager n = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 100, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel canal1 = new NotificationChannel("default", "teste", NotificationManager.IMPORTANCE_DEFAULT);
            canal1.setDescription("descricao");
            n.createNotificationChannel(canal1);
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_CALL)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("teste")
                .setContentText("dgsudhfjksh");



        n.notify(100, builder.build());*/

        getComponentes();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        c = Facade.getmInstance().selectContas(Facade.getmInstance().getUsuarioLogado());

        if(c != null){
            soma = Facade.getmInstance().calculaSaldoTotal(c);
            NumberFormat nf = new DecimalFormat("0.00");
            sal = findViewById(R.id.saldo);
            sal.setText(String.valueOf(nf.format(soma)));

            RecyclerView rc = findViewById(R.id.rec);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rc.setLayoutManager(lm);
            RecyclerView.Adapter adap =  new ContaRadapter(c);
            rc.setAdapter(adap);


        }

        carregarGrafico();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void NotificaContasAtrasadas() {
        List<Operacao> atrasadas = Facade.getmInstance().getContasAtrasadas(Facade.getmInstance().getUsuarioLogado());
        if(atrasadas.isEmpty() || atrasadas == null){

        }else{
            showWithButtons();
        }
    }

    private void calculaContas() {

        List<Operacao> contasPagar = Facade.getmInstance().getDebitosFuturos(Facade.getmInstance().getUsuarioLogado());
        List<Operacao> contasReceber = Facade.getmInstance().getCreditosFuturos(Facade.getmInstance().getUsuarioLogado());
        List<Operacao> contasAtrasadas = Facade.getmInstance().getContasAtrasadas(Facade.getmInstance().getUsuarioLogado());

        NavigationView nv = findViewById(R.id.nav_view);
        View head = nv.getHeaderView(0);

        TextView tvpagar = (TextView) head.findViewById(R.id.textView9);
        TextView tvreceber = head.findViewById(R.id.textView7);
        TextView tvatrasadas = head.findViewById(R.id.textView3);
        tvpagar.setText(String.valueOf(contasPagar.size()));
        tvreceber.setText(String.valueOf(contasReceber.size()));
        tvatrasadas.setText(String.valueOf(contasAtrasadas.size()));

        tvpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("titulo",getString(R.string.Capagar));
                Intent it = new Intent(MainActivity.this, ContasA.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        tvreceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("titulo",getString(R.string.Creceber));
                Intent it = new Intent(MainActivity.this, ContasA.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        tvatrasadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("titulo",getString(R.string.Catrasadas));
                Intent it = new Intent(MainActivity.this, ContasA.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });

    }

    public void showWithButtons(){

        Calendar calend = Calendar.getInstance();
        Intent it = new Intent(getApplicationContext(),MyBroadcast.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 100, it, PendingIntent.FLAG_UPDATE_CURRENT);

        calend.set(Calendar.HOUR_OF_DAY, 10);
        calend.set(Calendar.MINUTE, 6);
        calend.set(Calendar.SECOND, 10);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calend.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);


    }

    public NotificationCompat.Builder newBuilderNotification(String title, String text, boolean isAutoCancel){
        //Instancia a Intent para abrir o cadastro do telefone passado como argumento.
        Intent intentNotification = new Intent(this, MainActivity.class);

        //Instancia uma PedingIntent para ser associado a notificação.
        PendingIntent pendingIntent = PendingIntent.getActivity(this
                , (int) System.currentTimeMillis()
                , intentNotification
                , 0);

        //Constroi a notificação sobre o telefone passado como argumento.
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setAutoCancel(isAutoCancel)
                        .setSmallIcon(R.mipmap.ic_launcher_round)

                        .setContentTitle(title)
                        .setContentText(text);

        //Retorna a referência do objeto notificação.
        return builder;
    }



    private void carregarGrafico() {

        ArrayList<String >CategDeb;
        ArrayList<PieEntry> valoresDebitos = new ArrayList<>();
        float totalDebitos = 0;

        pieChat = findViewById(R.id.pie_chat);
        pieChat.setUsePercentValues(true);
        pieChat.getDescription().setEnabled(false);
        pieChat.setExtraOffsets(5, 10, 5, 5);
        pieChat.setDragDecelerationFrictionCoef(0.95f);
        pieChat.setDrawHoleEnabled(true);
        pieChat.setHoleColor(Color.WHITE);
        pieChat.setTransparentCircleRadius(21f);

        Calendar calen = Calendar.getInstance();
        String mesAtual = "0"+String.valueOf(calen.get(Calendar.MONTH)+1);
        totalDebitos = Facade.getmInstance().getTotalDebitos("0"+String.valueOf(calen.get(Calendar.MONTH)+1), String.valueOf(calen.get(Calendar.YEAR)));
        CategDeb = Facade.getmInstance().DebitosPorCategoria(mesAtual, String.valueOf(calen.get(Calendar.YEAR)));



        int k = 0;
        while (k < CategDeb.size()){
            float porcent =  Float.valueOf(Float.valueOf(CategDeb.get(k+1))) / totalDebitos;
            valoresDebitos.add(new PieEntry((porcent * 100), CategDeb.get(k)));
            k = k + 2;
        }


        pieChat.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet pieDataset = new PieDataSet(valoresDebitos, "");
        pieDataset.setSliceSpace(3f);
        pieDataset.setSelectionShift(5f);
        pieDataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pd = new PieData((pieDataset));
        pd.setValueTextSize(10f);
        pd.setValueTextColor(Color.YELLOW);


        pieChat.setData(pd);
    }

    private void animateFab(){
        if(fabIsOpen){
            fab.startAnimation(rotacaoV);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            cred.startAnimation(fabClose);
            debt.startAnimation(fabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fabIsOpen = false;
        }
        else{
            fab.startAnimation(rotacaoF);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            cred.startAnimation(fabOpen);
            debt.startAnimation(fabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fabIsOpen = true;
        }
    }

    public void getComponentes(){

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotacaoF = AnimationUtils.loadAnimation(this, R.anim.rotacao_frente);
        rotacaoV = AnimationUtils.loadAnimation(this, R.anim.rotacao_volta);
        cred = (TextView) findViewById(R.id.cred);
        debt = (TextView) findViewById(R.id.debt);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("tipoOperacao", getResources().getString(R.string.creditar));
                Intent i = new Intent(MainActivity.this, ActivityOperacao.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("tipoOperacao", getResources().getString(R.string.debitar));
                Intent i = new Intent(MainActivity.this, ActivityOperacao.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {
            user.setLogado(0);
            try {
                Facade.getmInstance().saveUsuario(user);
            } catch (SaveException e) {
                e.printStackTrace();
            }
            Intent it = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(it);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent it = new Intent(MainActivity.this, MainActivity.class);
            startActivity(it);

        } else if (id == R.id.configConta) {
            Intent it = new Intent(MainActivity.this, CadastroConta.class);
            startActivity(it);


        } else if (id == R.id.nav_gallery) {
            Calendar calendario = Calendar.getInstance();
            SimpleDateFormat nome = new SimpleDateFormat("MMMM");
            String nomeMes = nome.format(calendario.getTime());

            Bundle bundle = new Bundle();
            bundle.putString("mes", "0"+String.valueOf(calendario.get(Calendar.MONTH)+1));
            bundle.putString("descricao", nomeMes);
            bundle.putString("ano", String.valueOf(calendario.get(Calendar.YEAR)));
            Intent it = new Intent(MainActivity.this, HistoricoMensal.class);
            it.putExtras(bundle);
            startActivity(it);

        } else if (id == R.id.nav_slideshow) {

            Calendar calendario = Calendar.getInstance();
            SimpleDateFormat nome = new SimpleDateFormat("MMMM");
            String nomeMes = nome.format(calendario.getTime());

            Bundle bundle = new Bundle();
            bundle.putString("mes", "0"+String.valueOf(calendario.get(Calendar.MONTH)+1));
            bundle.putString("descricao", nomeMes);
            bundle.putString("ano", String.valueOf(calendario.get(Calendar.YEAR)));
            Intent it = new Intent(MainActivity.this, Graficos.class);
            it.putExtras(bundle);
            startActivity(it);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
