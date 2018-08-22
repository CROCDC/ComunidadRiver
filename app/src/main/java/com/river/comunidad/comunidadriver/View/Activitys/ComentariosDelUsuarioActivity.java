package com.river.comunidad.comunidadriver.View.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.river.comunidad.comunidadriver.Controler.ControllerNoticiaFirebase;
import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.util.List;

public class ComentariosDelUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaDeComentariosDelUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_del_usuario);

        new ControllerNoticiaFirebase(getApplicationContext()).pedirListaDeComentariosDeUnUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid(), new ResultListener<List<Comentario>>() {
            @Override
            public void finish(List<Comentario> resultado) {
                Toast.makeText(ComentariosDelUsuarioActivity.this, resultado.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewListaDeComentariosDelUsuario = findViewById(R.id.recyclerViewListaDeComentariosDelUsuario_activitycomentariosdelusuario);


    }


}
