package com.river.comunidad.comunidadriver.View.Activitys;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.river.comunidad.comunidadriver.Controller.ControllerPosteosFirebase;
import com.river.comunidad.comunidadriver.DAO.DAOPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.Model.Firebase.DisLike;
import com.river.comunidad.comunidadriver.Model.Firebase.Like;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class CrearPosteoActivity extends AppCompatActivity {




    private Toolbar toolbar;
    private ImageView imageViewDeUsuario;
    private TextView textViewNombreDelUsuario;
    private EditText editTextDelPosteo;
    private ImageView imageViewPreview;
    private LinearLayout linearLayoutContenedorOpcionGaleria;
    private LinearLayout linearLayoutContenedorOpcionCamara;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private File imageFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_posteo);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        imageViewDeUsuario = findViewById(R.id.imageViewUsuario_crearposteoactivity);
        textViewNombreDelUsuario = findViewById(R.id.textViewNombreDeUsuario_crearposteoactivity);
        editTextDelPosteo = findViewById(R.id.editTextDelPosteo_crearposteoactivity);
        imageViewPreview = findViewById(R.id.imageViewPreview_crearposteoactivity);
        linearLayoutContenedorOpcionGaleria = findViewById(R.id.linearLayoutContenedorOpcionAbrirGaleria_crearposteoacivity);
        linearLayoutContenedorOpcionCamara = findViewById(R.id.linearLayoutContenedorOpcionAbrirCamara_crearposteoactivity);


        setSupportActionBar(toolbar);

        Glide.with(getApplicationContext())
                .load(user.getPhotoUrl())
                .into(imageViewDeUsuario);
        textViewNombreDelUsuario.setText(user.getDisplayName());



        linearLayoutContenedorOpcionGaleria.setOnClickListener(new TouchLinearLayoutContenedorOpcionGaleria());
        linearLayoutContenedorOpcionCamara.setOnClickListener(new TouchLinearLayoutContenedorOpcionCamara());



    }



    public class TouchLinearLayoutContenedorOpcionGaleria implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EasyImage.openGallery(CrearPosteoActivity.this,666);
        }
    }

    public class TouchLinearLayoutContenedorOpcionCamara implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            EasyImage.openCamera(CrearPosteoActivity.this,666);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        EasyImage.handleActivityResult(requestCode,resultCode,data,CrearPosteoActivity.this, new DefaultCallback() {
            @Override
            public void onImagePicked(File file, EasyImage.ImageSource source, int type) {
                imageFile = file;
                imageViewPreview.setVisibility(View.VISIBLE);

                Glide.with(getApplicationContext())
                        .load(file)
                        .into(imageViewPreview);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Posteo posteo = new Posteo(user.getDisplayName(),
                                   System.currentTimeMillis(),
                                   2,
                                    user.getPhotoUrl().toString(),
                                    user.getUid(),
                                     editTextDelPosteo.getText().toString(),
                                    new Like(),
                                    new DisLike(),
                                    new ArrayList<Comentario>());

        new DAOPosteosFirebase().subirImagenDelPosteoAFireStorage(imageFile, new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                resultado.toString();
            }
        });

        new ControllerPosteosFirebase().publicarPosteo(posteo, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                if (resultado){
                    FancyToast.makeText(getApplicationContext(),"Posteo subido",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                }
            }
        });

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu_compartir,menu);
        return true;
    }
}
