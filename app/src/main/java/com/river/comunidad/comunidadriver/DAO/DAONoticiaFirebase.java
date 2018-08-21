package com.river.comunidad.comunidadriver.DAO;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class DAONoticiaFirebase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Context context;
    private FirebaseUser user;

    public DAONoticiaFirebase(Context context) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;

    }

    public void verficiarSiLaNoticiaEstaEnFirebase(final Noticia noticia, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_CONTENIDO_FAVORITO).child(user.getUid()).child(noticia.getId().toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    escuchadorDelControlador.finish(false);
                } else {
                    escuchadorDelControlador.finish(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void agregarLaNoticiaAGuardado(Noticia noticia, final ResultListener<Boolean> escuchadorDelControlador) {
        try {
            databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_CONTENIDO_FAVORITO).child(user.getUid());
            databaseReference.child(noticia.getId().toString()).setValue(new Noticia(noticia.getLink(), noticia.getTitle(), noticia.getContent(), noticia.getExcerpt(), noticia.getEmbedded(), noticia.getCategories()));
            escuchadorDelControlador.finish(true);
        } catch (Exception e) {
            escuchadorDelControlador.finish(false);
        }
    }

    public void publicarComentario(final Integer idNoticia, final Comentario comentario, final ResultListener<Boolean> escuchadorDelControlador) {
        try {

            databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString());

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() == 0){
                        databaseReference.child("1").setValue(comentario);

                    }else {
                        long cuenta = dataSnapshot.getChildrenCount() + 1;
                        databaseReference.child(String.valueOf(cuenta)).setValue(comentario);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            escuchadorDelControlador.finish(true);
        } catch (Exception e) {
            escuchadorDelControlador.finish(false);
        }
    }

    public void pedirComentariosDeUnaNoticia(final Integer idNoticia, final ResultListener<List<Comentario>> escuchadorDelControlador) {
        try {
            databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Comentario> listaDeComentarios = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Comentario comentario = snapshot.getValue(Comentario.class);


                        if (comentario.getIdNoticia().equals(idNoticia)) {
                            listaDeComentarios.add(comentario);
                        }
                        escuchadorDelControlador.finish(listaDeComentarios);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            FancyToast.makeText(context, "a ocurrido un eror", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }
}
