package com.morganoliveira.contabills.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
*/
import com.morganoliveira.contabills.R;

import java.util.ArrayList;
import java.util.List;

//import DAO.FirebaseConfig;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.exceptions.UsuarioExistente;
import com.morganoliveira.contabills.model.Usuario;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity{



    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText user;
    private EditText mPasswordView;
    private TextView cadastro;

   // private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Set up the login form.
        user = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        cadastro = (TextView) findViewById(R.id.cadastrar);

        cadastro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nIn = new Intent(LoginActivity.this, Cadastro.class);
                startActivity(nIn);

            }
        });


        TextView mEmailSignInButton = (TextView) findViewById(R.id.entrar);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // attemptLogin();
                boolean flag = false;

                    flag = Facade.getmInstance().existUser(user.getText().toString(), mPasswordView.getText().toString());

                if(flag){
                    Usuario user2 = Facade.getmInstance().getUsuario(user.getText().toString(), mPasswordView.getText().toString());
                    user2.setLogado(1);
                    try {
                        Facade.getmInstance().saveUsuario(user2);
                    } catch (SaveException e) {
                        e.printStackTrace();
                    }

                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, R.string.usuarioInvalido, Toast.LENGTH_SHORT).show();
                }



            }
        });


    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }






    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

    }



}

