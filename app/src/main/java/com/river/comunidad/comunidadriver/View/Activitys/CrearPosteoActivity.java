package com.river.comunidad.comunidadriver.View.Activitys;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.river.comunidad.comunidadriver.Controller.ControllerPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.Model.Firebase.DisLike;
import com.river.comunidad.comunidadriver.Model.Firebase.Like;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.R;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class CrearPosteoActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView imageViewDeUsuario;
    private TextView textViewNombreDelUsuario;
    private EditText editTextDelPosteo;
    private ImageView imageViewPreview;
    private VideoView videoViewPreview;
    private LinearLayout linearLayoutContenedorOpcionGaleria;
    private LinearLayout linearLayoutContenedorOpcionCamara;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private File videoFile;
    private File imageFile;
    private ControllerPosteosFirebase controllerPosteosFirebase;
    private MediaController mediaController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_posteo);

        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        imageViewDeUsuario = findViewById(R.id.imageViewUsuario_crearposteoactivity);
        textViewNombreDelUsuario = findViewById(R.id.textViewNombreDeUsuario_crearposteoactivity);
        editTextDelPosteo = findViewById(R.id.editTextDelPosteo_crearposteoactivity);
        imageViewPreview = findViewById(R.id.imageViewPreview_crearposteoactivity);
        videoViewPreview = findViewById(R.id.videoViewPreview_crearposteoactivity);
        linearLayoutContenedorOpcionGaleria = findViewById(R.id.linearLayoutContenedorOpcionAbrirGaleria_crearposteoacivity);
        linearLayoutContenedorOpcionCamara = findViewById(R.id.linearLayoutContenedorOpcionAbrirCamara_crearposteoactivity);

        controllerPosteosFirebase = new ControllerPosteosFirebase();
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoViewPreview);

        setSupportActionBar(toolbar);

        Glide.with(getApplicationContext())
                .load(user.getPhotoUrl())
                .into(imageViewDeUsuario);
        textViewNombreDelUsuario.setText(user.getDisplayName());


        linearLayoutContenedorOpcionGaleria.setOnClickListener(new TouchLinearLayoutContenedorOpcionVideo());
        linearLayoutContenedorOpcionCamara.setOnClickListener(new TouchLinearLayoutContenedorOpcionCamara());


    }


    private class TouchLinearLayoutContenedorOpcionVideo implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new VideoPicker.Builder(CrearPosteoActivity.this)
                    .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                    .directory(VideoPicker.Directory.DEFAULT)
                    .extension(VideoPicker.Extension.MP4)
                    .enableDebuggingMode(true)
                    .build();
        }
    }

    public class TouchLinearLayoutContenedorOpcionGaleria implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    public class TouchLinearLayoutContenedorOpcionCamara implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE ){
            List<String> paths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
            videoViewPreview.setVisibility(View.VISIBLE);
            videoViewPreview.setVideoPath(paths.get(0));
            videoViewPreview.start();



            videoFile = new File(paths.get(0));

        }






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Posteo posteo = new Posteo(user.getDisplayName(),
                System.currentTimeMillis(),
                1,
                user.getPhotoUrl().toString(),
                user.getUid(),
                editTextDelPosteo.getText().toString(),
                new Like(),
                new DisLike(),
                new ArrayList<Comentario>());


        if (imageFile != null) {
            subirPosteoConImagen( imageFile);
        } else if (videoFile !=null){
            subirPosteoConVideo(videoFile);
        }else {
            subirPosteoConTexto();
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu_compartir, menu);
        return true;
    }

    private void subirPosteoConImagen(File imageFile) {

        final Posteo posteo = new Posteo(user.getDisplayName(),
                System.currentTimeMillis(),
                1,
                user.getPhotoUrl().toString(),
                user.getUid(),
                editTextDelPosteo.getText().toString(),
                new Like(),
                new DisLike(),
                new ArrayList<Comentario>());

        Intent intent = new Intent(CrearPosteoActivity.this, PosteosDeLosUsuariosActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(PosteosDeLosUsuariosActivity.CLAVE_POSTEO, posteo);
        bundle.putSerializable(PosteosDeLosUsuariosActivity.CLAVE_FILE_IMAGE, imageFile);

        intent.putExtras(bundle);

        setResult(1, intent);

        finish();


    }

    private void subirPosteoConVideo(File videoFile){
        final Posteo posteo = new Posteo(user.getDisplayName(),
                System.currentTimeMillis(),
                2,
                user.getPhotoUrl().toString(),
                user.getUid(),
                editTextDelPosteo.getText().toString(),
                new Like(),
                new DisLike(),
                new ArrayList<Comentario>());

        Intent intent = new Intent(CrearPosteoActivity.this, PosteosDeLosUsuariosActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(PosteosDeLosUsuariosActivity.CLAVE_POSTEO, posteo);
        bundle.putSerializable(PosteosDeLosUsuariosActivity.CLAVE_FILE_VIDEO, videoFile);

        intent.putExtras(bundle);

        setResult(2, intent);

        finish();
    }

    private void subirPosteoConTexto() {

        final Posteo posteo = new Posteo(user.getDisplayName(),
                System.currentTimeMillis(),
                3,
                user.getPhotoUrl().toString(),
                user.getUid(),
                editTextDelPosteo.getText().toString(),
                new Like(),
                new DisLike(),
                new ArrayList<Comentario>());


        Intent intent = new Intent(CrearPosteoActivity.this, PosteosDeLosUsuariosActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(PosteosDeLosUsuariosActivity.CLAVE_POSTEO, posteo);

        intent.putExtras(bundle);

        setResult(3, intent);

        finish();
    }




}
