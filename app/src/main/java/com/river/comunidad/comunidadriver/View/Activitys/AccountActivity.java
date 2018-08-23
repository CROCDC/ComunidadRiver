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


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.jackandphantom.blurimage.BlurImage;
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

    private Toolbar toolbar;


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

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Glide.with(getApplicationContext())
                    .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                    .into(circleImageViewUsuario);

            textViewNombreDeUsuario.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            cardViewButtonIniciarSesion.setVisibility(View.GONE);

        } else {
            textViewNombreDeUsuario.setText("Invitado");
            cardViewButtonCerrarSesion.setVisibility(View.GONE);

        }

        touchCardViewButtonCerrarSesion();
        touchCardViewButtonIniciarSesion();
        touchCardViewButtonGuardado();
        touchCardViewButtonComentarios();

        BlurImage.with(getApplicationContext()).load(R.drawable.cancha).intensity(40).Async(true).into(imageViewBackgroundMenu);


    }

    public void touchCardViewButtonCerrarSesion() {
        cardViewButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FancyToast.makeText(getApplicationContext(), "sesion cerrada", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                cardViewButtonCerrarSesion.setVisibility(View.GONE);
                cardViewButtonIniciarSesion.setVisibility(View.VISIBLE);
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

            }
        });
    }

    public void touchCardViewButtonComentarios() {
        cardViewButtonComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ComentariosDelUsuarioActivity.class));
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
