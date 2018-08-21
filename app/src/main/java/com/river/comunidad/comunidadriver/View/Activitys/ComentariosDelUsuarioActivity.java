package com.river.comunidad.comunidadriver.View.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.river.comunidad.comunidadriver.Controler.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.R;

public class ComentariosDelUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaDeComentariosDelUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_del_usuario);

        


        recyclerViewListaDeComentariosDelUsuario = findViewById(R.id.recyclerViewListaDeComentariosDelUsuario_activitycomentariosdelusuario);


    }


}
