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
import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;

import com.river.comunidad.comunidadriver.Model.Firebase.Comentario;
import com.river.comunidad.comunidadriver.Model.Firebase.Respuesta;
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

    public void verficiarSiLaNoticiaEstaEnGuardado(final Noticia noticia, final ResultListener<Boolean> escuchadorDelControlador) {
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
                error();
            }
        });

    }

    public void pedirListaDeNoticiasGuardadasDelUsuario(final ResultListener<List<Noticia>> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_CONTENIDO_FAVORITO).child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Noticia> listaDeNotiicas = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Noticia noticia = snapshot.getValue(Noticia.class);
                    listaDeNotiicas.add(noticia);

                }
                escuchadorDelControlador.finish(listaDeNotiicas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });
    }


    public void verificarSiElUsuarioYaReaccionoAlComentario(String reaccion, Integer idNoticia, String idComentario, final ResultListener<Boolean> escuchadorDelControlador) {
        final List<String> listaDeUsuarios = new ArrayList<>();


        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child(reaccion).child("usuarios");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String usuarioUID = snapshot.getValue(String.class);
                        listaDeUsuarios.add(usuarioUID);
                    }

                    if (listaDeUsuarios.contains(user.getUid())) {
                        escuchadorDelControlador.finish(true);
                    } else {
                        escuchadorDelControlador.finish(false);
                    }


                } else {
                    escuchadorDelControlador.finish(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });
    }

    public void verificarSiElUsuarioYaReaccionoALaRespuesta(String reaccion, Integer idNoticia, String idComentario, Integer idRespuesta, final ResultListener<Boolean> escuchadorDelControlador) {
        final List<String> listaDeUsuarios = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("listaDeRespuestas").child(idRespuesta.toString()).child(reaccion).child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String usuarioUID = snapshot.getValue(String.class);
                        listaDeUsuarios.add(usuarioUID);
                    }

                    if (listaDeUsuarios.contains(user.getUid())) {
                        escuchadorDelControlador.finish(true);
                    } else {
                        escuchadorDelControlador.finish(false);
                    }


                } else {
                    escuchadorDelControlador.finish(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });
    }

    public void darReaccionAUnComentario(final String reaccion, final Integer idNoticia, final String idComentario, final ResultListener<Boolean> escuchadorDelControlador) {

        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child(reaccion).child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> listaDeUsuarios = (List<String>) dataSnapshot.getValue();

                try {
                    listaDeUsuarios.add(user.getUid());
                    databaseReference.setValue(listaDeUsuarios);
                    escuchadorDelControlador.finish(true);
                } catch (Exception e) {
                    databaseReference.child("0").setValue(user.getUid());
                    escuchadorDelControlador.finish(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                escuchadorDelControlador.finish(false);
            }


        });
    }

    public void descontarReaccionAUnComentario(final String reaccion, final Integer idNoticia, final String idComentario, final ResultListener<Boolean> escuchadorDelControlador) {

        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child(reaccion).child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> listaDeUsuarios = (List<String>) dataSnapshot.getValue();

                try {
                    listaDeUsuarios.remove(user.getUid());

                } catch (Exception e) {
                    escuchadorDelControlador.finish(true);
                }

                databaseReference.setValue(listaDeUsuarios);

                escuchadorDelControlador.finish(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                escuchadorDelControlador.finish(false);
            }


        });
    }


    public void darReaccionAUnaRespuesta(final String reaccion, final Integer idNoticia, final String idComentario, final Integer idRespuesta, final ResultListener<Boolean> escuchadorDelControlador) {

        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("listaDeRespuestas").child(idRespuesta.toString()).child(reaccion).child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> listaDeUsuarios = (List<String>) dataSnapshot.getValue();

                try {
                    listaDeUsuarios.add(user.getUid());
                    databaseReference.setValue(listaDeUsuarios);
                    escuchadorDelControlador.finish(true);
                } catch (Exception e) {
                    databaseReference.child("0").setValue(user.getUid());
                    escuchadorDelControlador.finish(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                escuchadorDelControlador.finish(false);
            }


        });
    }

    public void descontarReaccionAUnaRespuesta(final String reaccion, final Integer idNoticia, final String idComentario, Integer idRespuesta, final ResultListener<Boolean> escuchadorDelControlador) {

        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("listaDeRespuestas").child(idRespuesta.toString()).child(reaccion).child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> listaDeUsuarios = (List<String>) dataSnapshot.getValue();

                try {
                    listaDeUsuarios.remove(user.getUid());

                } catch (Exception e) {
                    escuchadorDelControlador.finish(true);
                }

                databaseReference.setValue(listaDeUsuarios);

                escuchadorDelControlador.finish(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                escuchadorDelControlador.finish(false);
            }


        });
    }

    public void agregarLaNoticiaAGuardado(Noticia noticia,final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_CONTENIDO_FAVORITO).child(user.getUid());
        databaseReference.child(noticia.getId().toString()).setValue(new Noticia(noticia.getLink(), noticia.getTitle(), noticia.getContent(), noticia.getExcerpt(), noticia.getEmbedded(), noticia.getCategories()));
        escuchadorDelControlador.finish(true);
    }

    public void publicarComentario(final Integer idNoticia, final Comentario comentario,
                                   final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    databaseReference.child(comentario.getIdComentario()).setValue(comentario);
                    escuchadorDelControlador.finish(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });


    }


    public void publicarRespuestas(final Integer idNoticia, final String idComentario, final Respuesta respuestaF, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("listaDeRespuestas");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() == 0) {
                    databaseReference.child("0").setValue(respuestaF);
                    escuchadorDelControlador.finish(true);
                } else {
                    long cuenta = dataSnapshot.getChildrenCount();
                    databaseReference.child(String.valueOf(cuenta)).setValue(respuestaF);
                    escuchadorDelControlador.finish(true);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void pedirListaDeComentariosDeUnaNoticia(final Integer idNoticia,final ResultListener<List<Comentario>> escuchadorDelControlador) {
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

                }
                try {
                    escuchadorDelControlador.finish(listaDeComentarios);

                } catch (Exception e) {
                    e.toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });

    }

    public void pedirListaDeComentariosDeUnUsuario(final ResultListener<List<Comentario>> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Comentario> listaDeComentariosDelUsuario = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getKey();
                    for (DataSnapshot snapshotComentario : snapshot.getChildren()) {
                        Comentario comentario = snapshotComentario.getValue(Comentario.class);

                        if (comentario.getUsuarioUID().equals(user.getUid())) {
                            listaDeComentariosDelUsuario.add(comentario);
                        }
                    }
                }

                escuchadorDelControlador.finish(listaDeComentariosDelUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });
    }


    public void error() {
        FancyToast.makeText(context, "a ocurrido un error", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();


    }

}
