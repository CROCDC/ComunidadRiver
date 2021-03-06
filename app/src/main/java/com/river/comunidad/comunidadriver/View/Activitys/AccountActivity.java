package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountActivity extends AppCompatActivity {

    private CircleImageView circleImageViewUsuario;
    private TextView textViewNombreDeUsuario;
    private ImageView imageViewBackgroundMenu;

    private CardView cardViewButtonCerrarSesion;
    private CardView cardViewButtonIniciarSesion;
    private CardView cardViewButtonGuardado;
    private CardView cardViewButtonComentarios;
    private CardView cardViewButtonPosteos;

    private Toolbar toolbar;

    private String nombreDeUsuario;
    private String fotoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        circleImageViewUsuario = findViewById(R.id.circleImageViewUsuario_activityaccount);
        textViewNombreDeUsuario = findViewById(R.id.textViewNombreDeUsuario_activityaccount);
        imageViewBackgroundMenu = findViewById(R.id.imageViewBackgroundMenu_activityaccount);
        cardViewButtonCerrarSesion = findViewById(R.id.cardViewButtonCerrarSesion_activityaccount);
        cardViewButtonIniciarSesion = findViewById(R.id.cardViewButtonIniciarSesion_activityaccount);
        cardViewButtonGuardado = findViewById(R.id.cardViewButtonGuardado_activityaccount);
        cardViewButtonComentarios = findViewById(R.id.cardViewButtonComentarios_activityaccount);
        cardViewButtonPosteos = findViewById(R.id.cardViewButtonPosteos_activityaccount);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            try {
                fotoUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
            } catch (Exception e) {
                fotoUrl = "http://www.comunidadriver.com/wp-content/uploads/2018/08/cuenta.png";
            }


            Glide.with(getApplicationContext())
                    .load(fotoUrl)
                    .into(circleImageViewUsuario);


            if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName().equals("")) {
                textViewNombreDeUsuario.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            }else {
                textViewNombreDeUsuario.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            }

            cardViewButtonIniciarSesion.setVisibility(View.GONE);

        } else {
            textViewNombreDeUsuario.setText("Invitado");
            cardViewButtonCerrarSesion.setVisibility(View.GONE);

        }

        touchCardViewButtonCerrarSesion();
        touchCardViewButtonIniciarSesion();
        touchCardViewButtonGuardado();
        touchCardViewButtonComentarios();
        touchCardViewButtonPosteos();

        //BlurImage.with(getApplicationContext()).load(R.drawable.cancha).intensity(40).Async(true).into(imageViewBackgroundMenu);


    }

    public void touchCardViewButtonCerrarSesion() {
        cardViewButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FancyToast.makeText(getApplicationContext(), "sesion cerrada", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                cardViewButtonCerrarSesion.setVisibility(View.GONE);
                cardViewButtonIniciarSesion.setVisibility(View.VISIBLE);
                textViewNombreDeUsuario.setText("Invitado");
                circleImageViewUsuario.setImageResource(R.drawable.cuenta);

            }
        });
    }

    public void touchCardViewButtonIniciarSesion() {
        cardViewButtonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
    }

    public void touchCardViewButtonGuardado() {
        cardViewButtonGuardado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent intent = new Intent(AccountActivity.this, NoticiasGuardadasActivity.class);
                    startActivity(intent);
                } else {
                    FancyToast.makeText(getApplicationContext(), "Debes estas logueado para acceder a esta funcion ", Toast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }
        });
    }

    public void touchCardViewButtonComentarios() {
        cardViewButtonComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(AccountActivity.this, ComentariosDelUsuarioActivity.class));

                } else {
                    FancyToast.makeText(getApplicationContext(), "Debes estas logueado para acceder a esta funcion ", Toast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            }
        });
    }

    public void touchCardViewButtonPosteos(){
        cardViewButtonPosteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,PosteosDeLosUsuariosActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_account, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 16908332:
                AccountActivity.this.onBackPressed();
                break;
        }
        return true;
    }
}
