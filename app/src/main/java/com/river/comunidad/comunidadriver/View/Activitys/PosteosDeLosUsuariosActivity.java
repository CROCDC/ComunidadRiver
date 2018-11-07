package com.river.comunidad.comunidadriver.View.Activitys;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.river.comunidad.comunidadriver.Controller.ControllerPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.View.Adapters.ListaDePosteosAdapter;

import java.io.File;
import java.util.List;

public class PosteosDeLosUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaDePosteos;
    private ListaDePosteosAdapter listaDePosteosAdapter;
    private FloatingActionButton floatingActionButtonSubirPosteo;
    private Toolbar toolbar;
    private ControllerPosteosFirebase controllerPosteosFirebase;
    private Integer progress = 0;

    public static final String CLAVE_POSTEO = "posteo";
    public static final String CLAVE_FILE_IMAGE = "imageFile";
    public static final String CLAVE_FILE_VIDEO = "videoFile";

    private String ADMIN_CHANNEL_ID = "random 1 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posteos_de_los_usuarios);


        toolbar = findViewById(R.id.toolbarPrincipal_toolbar);
        recyclerViewListaDePosteos = findViewById(R.id.recyclerViewListaDePosteosDeLosUsuarios_activityposteosdelosusuarios);
        floatingActionButtonSubirPosteo = findViewById(R.id.floatingButtonSubirPosteo);

        listaDePosteosAdapter = new ListaDePosteosAdapter();
        controllerPosteosFirebase = new ControllerPosteosFirebase();

        pedirListaDePosteos();

        setSupportActionBar(toolbar);

        recyclerViewListaDePosteos.setAdapter(listaDePosteosAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PosteosDeLosUsuariosActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerViewListaDePosteos.setLayoutManager(linearLayoutManager);

        floatingActionButtonSubirPosteo.setOnClickListener(new TouchFloatingActionButtonSubirPosteo());

    }


    public void pedirListaDePosteos() {
        new ControllerPosteosFirebase().obtenerPosteosDeLosUsuarios(new ResultListener<List<Posteo>>() {
            @Override
            public void finish(List<Posteo> resultado) {
                listaDePosteosAdapter.setListaDePosteos(resultado);
            }
        });
    }

    public class TouchFloatingActionButtonSubirPosteo implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(PosteosDeLosUsuariosActivity.this, CrearPosteoActivity.class), 10);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 1) {
                Intent intentRecibido = data;

                Bundle bundle = intentRecibido.getExtras();

                Posteo posteo = (Posteo) bundle.getSerializable(CLAVE_POSTEO);

                File imageFile = (File) bundle.getSerializable(CLAVE_FILE_IMAGE);

                subirPosteoConImageFile(posteo, imageFile);
            } else if (resultCode == 2) {
                Intent intentRecibido = data;

                Bundle bundle = intentRecibido.getExtras();

                Posteo posteo = (Posteo) bundle.getSerializable(CLAVE_POSTEO);

                File videoFile = (File) bundle.getSerializable(CLAVE_FILE_VIDEO);

                subirPosteoConVideoFile(videoFile, posteo);


            } else if (resultCode == 3) {

                Intent intentRecibido = data;

                Bundle bundle = intentRecibido.getExtras();

                Posteo posteo = (Posteo) bundle.getSerializable(CLAVE_POSTEO);


                subirPosteoConTexto(posteo);
            }
        }

        pedirListaDePosteos();
    }


    public void subirPosteoConImageFile(Posteo posteo, File imageFile) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.logofondonegropequeno)
                .setContentTitle("Subiendo Imagen")
                .setProgress(100, progress, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, mBuilder.build());


        controllerPosteosFirebase.subirImageFileDelPosteoAFireStorage(imageFile, new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                posteo.setElementoMultimedial(resultado);
                controllerPosteosFirebase.publicarPosteo(posteo, new ResultListener<Boolean>() {
                    @Override
                    public void finish(Boolean resultado) {
                        if (resultado) {
                            pedirListaDePosteos();
                            notificationManager.cancel(1);

                        }

                    }
                });


            }
        }, new ResultListener<Integer>() {
            @Override
            public void finish(Integer resultado) {
                actualizarProgreso(resultado);
            }
        });
    }

    private void subirPosteoConVideoFile(File videoFile, Posteo posteo) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.logofondonegropequeno)
                .setContentTitle("Subiendo Video")

                .setProgress(100, progress, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, mBuilder.build());

        controllerPosteosFirebase.subirVideoFileDelPosteoAFireStorage(videoFile, new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                posteo.setElementoMultimedial(resultado);
                controllerPosteosFirebase.publicarPosteo(posteo, new ResultListener<Boolean>() {
                    @Override
                    public void finish(Boolean resultado) {
                        pedirListaDePosteos();
                        notificationManager.cancel(1);
                    }
                });


            }
        }, new ResultListener<Integer>() {
            @Override
            public void finish(Integer resultado) {
                actualizarProgreso(resultado);

            }
        });
    }

    public void subirPosteoConTexto(Posteo posteo) {
        controllerPosteosFirebase.publicarPosteo(posteo, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                pedirListaDePosteos();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ADMIN_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void actualizarProgreso(Integer progreso) {
        progress += progreso;
    }
}
