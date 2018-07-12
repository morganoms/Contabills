package com.morganoliveira.contabills.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Operacao;
import com.morganoliveira.contabills.model.Usuario;

public class Activity_splash extends AppCompatActivity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private Thread splashTread;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActiveAndroid.initialize(new Configuration.Builder(getApplicationContext())
                .addModelClasses(Usuario.class, Conta.class, Operacao.class, Categoria.class)
                .create());

        user = Facade.getmInstance().getUsuarioLogado();

    }

    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                    if(user == null){
                        Intent intent = new Intent(Activity_splash.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        Activity_splash.this.finish();
                    }else {
                        Intent intent = new Intent(Activity_splash.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        Activity_splash.this.finish();
                    }
                } catch (InterruptedException e) {
                    // do nothingContabills

                } finally {
                    Activity_splash.this.finish();
                }

            }
        };
        splashTread.start();

    }

}
