package com.river.comunidad.comunidadriver.View.Activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.CircleMenu.MainCircleActivity;
import com.river.comunidad.comunidadriver.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Pattern;


public class LoginNativoActivity extends AppCompatActivity {

    public static final String OPCION = "login o registrarse";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText editTextCampoEmail;
    private EditText editTextCampoContrasena;
    private TextView textViewRecuperarContraseña;
    private TextView textView;
    private CardView cardViewButtonIniciar;
    private CardView cardViewButtonRegistrar;


    private Boolean opcion;

    private String email;
    private String password;

    private Toolbar toolbar;

    public LoginNativoActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_nativo);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        opcion = bundle.getBoolean(OPCION);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        editTextCampoEmail = findViewById(R.id.editTextCampoEmail_fragmentloginnativo);
        editTextCampoContrasena = findViewById(R.id.editTextCampoContraseña_fragmentloginnativo);
        textView = findViewById(R.id.textView_fragmentloginnativo);
        textViewRecuperarContraseña = findViewById(R.id.textViewRecuperarContraseña_fragmentloginnativo);
        cardViewButtonIniciar = findViewById(R.id.cardViewButtonIniciarSesion_fragmentloginnativo);
        cardViewButtonRegistrar = findViewById(R.id.cardViewButtonRegistrarse_fragmentloginnativo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (opcion) {
            cardViewButtonIniciar.setVisibility(View.VISIBLE);
        } else {
            textViewRecuperarContraseña.setVisibility(View.GONE);
            cardViewButtonRegistrar.setVisibility(View.VISIBLE);
        }


        touchEditTextRecuperarContraseñaButton();
        touchLoginButton();
        touchRegisterButton();
    }

    public void touchEditTextRecuperarContraseñaButton() {
        textViewRecuperarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(getApplicationContext(), "ingrese su email", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                textView.setText("enviar email de recuparacion");
                textView.setTextSize(10);
                editTextCampoContrasena.setVisibility(View.GONE);

                cardViewButtonIniciar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!editTextCampoEmail.getText().toString().equals("")) {
                            if (validarEmail(editTextCampoEmail.getText().toString())) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(editTextCampoEmail.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    FancyToast.makeText(getApplicationContext(), "email enviado", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                                                }
                                            }
                                        });
                            } else {
                                FancyToast.makeText(getApplicationContext(), "verifique su email", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                            }

                        } else {
                            FancyToast.makeText(getApplicationContext(), "ingrese su email", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });
    }

    public void touchLoginButton() {
        cardViewButtonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editTextCampoEmail.getText().toString().equals("") && !editTextCampoContrasena.getText().toString().equals("")) {
                    email = editTextCampoEmail.getText().toString();
                    password = editTextCampoContrasena.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loginExitoso();

                                    } else {
                                        FancyToast.makeText(getApplicationContext(), "verifique sus credenciales de acceso", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                                    }


                                }
                            });
                } else {
                    FancyToast.makeText(getApplicationContext(), "ingrese sus credenciales de acceso", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }


            }
        });
    }

    public void touchRegisterButton() {

        cardViewButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editTextCampoEmail.getText().toString().equals("") && !editTextCampoContrasena.getText().toString().equals("")) {
                    email = editTextCampoEmail.getText().toString();
                    password = editTextCampoContrasena.getText().toString();
                    if (validarEmail(email)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            loginExitoso();

                                        } else {
                                            loginFallido();
                                        }

                                    }
                                });
                    } else {
                        FancyToast.makeText(getApplicationContext(), "verifique sus credenciales de acceso", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }

                } else {
                    FancyToast.makeText(getApplicationContext(), "ingrese sus credenciales de acceso", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            }


        });


    }


    public void loginExitoso() {
        FancyToast.makeText(getApplicationContext(), "Login exitoso", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        startActivity(new Intent(getApplicationContext(), MainCircleActivity.class));

    }

    public void loginFallido() {
        FancyToast.makeText(getApplicationContext(), "hubo un error en el login", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 16908332:
                LoginNativoActivity.this.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
