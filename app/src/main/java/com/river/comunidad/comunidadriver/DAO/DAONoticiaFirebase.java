package com.river.comunidad.comunidadriver.DAO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    public void verificarSiElUsuarioYaLikeo(Integer idNoticia, Integer idComentario, final String usuarioUId, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("like").child("usuarios");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String usuarioUID = snapshot.getValue(String.class);
                        if (usuarioUID.equals(usuarioUId)) {
                            escuchadorDelControlador.finish(true);
                        } else {
                            escuchadorDelControlador.finish(false);
                        }
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

    public void verificarSiElUsuarioYaDislikeo(Integer idNoticia, Integer idComentario, final String usuarioUId, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("disLike").child("usuarios");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String usuarioUID = snapshot.getValue(String.class);
                        if (usuarioUID.equals(usuarioUId)) {
                            escuchadorDelControlador.finish(true);
                        } else {
                            escuchadorDelControlador.finish(false);
                        }
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

    public void descontarLikeAUnComentario(final Integer idNoticia, final Integer idComentario, final String usuarioUId, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("like").child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Integer i = 0; i <= dataSnapshot.getChildrenCount(); i++) {
                    databaseReference.child(i.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String usuario = dataSnapshot.getValue(String.class);

                            if (usuario != null){

                                if (usuario.equals(usuarioUId)) {
                                    databaseReference.removeValue();

                                    databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("like").child("cantLikes");


                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Integer cantLikes = dataSnapshot.getValue(Integer.class);
                                            databaseReference.setValue(cantLikes - 1);
                                            escuchadorDelControlador.finish(true);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            escuchadorDelControlador.finish(false);
                                        }
                                    });

                                }else {
                                    escuchadorDelControlador.finish(false);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void descontarDisLikeAUnComentario(final Integer idNoticia, final Integer idComentario, final String usuarioUId, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("disLike").child("usuarios");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Integer i = 0; i <= dataSnapshot.getChildrenCount(); i++) {
                    databaseReference.child(i.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String usuario = dataSnapshot.getValue(String.class);

                            if (usuario != null){
                                if (usuario.equals(usuarioUId)) {
                                    databaseReference.removeValue();

                                    databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("disLike").child("cantDisLikes");

                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Integer cantLikes = dataSnapshot.getValue(Integer.class);
                                            databaseReference.setValue(cantLikes - 1);
                                            escuchadorDelControlador.finish(true);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            escuchadorDelControlador.finish(false);
                                        }
                                    });
                                }
                            }else {
                                escuchadorDelControlador.finish(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void agregarLaNoticiaAGuardado(Noticia noticia, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_CONTENIDO_FAVORITO).child(user.getUid());
        databaseReference.child(noticia.getId().toString()).setValue(new Noticia(noticia.getLink(), noticia.getTitle(), noticia.getContent(), noticia.getExcerpt(), noticia.getEmbedded(), noticia.getCategories()));
        escuchadorDelControlador.finish(true);
    }

    public void publicarComentario(final Integer idNoticia, final Comentario comentario, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    databaseReference.child("1").setValue(comentario);
                    escuchadorDelControlador.finish(true);


                } else {
                    long cuenta = dataSnapshot.getChildrenCount() + 1;
                    databaseReference.child(String.valueOf(cuenta)).setValue(comentario);
                    escuchadorDelControlador.finish(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });


    }

    public void pedirListaDeComentariosDeUnaNoticia(final Integer idNoticia, final ResultListener<List<Comentario>> escuchadorDelControlador) {
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
                escuchadorDelControlador.finish(listaDeComentarios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });

    }

    public void pedirListaDeComentariosDeUnUsuario(final String usuarioUID, final ResultListener<List<Comentario>> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Comentario> listaDeComentariosDelUsuario = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getKey();
                    for (DataSnapshot snapshotComentario : snapshot.getChildren()) {
                        Comentario comentario = snapshotComentario.getValue(Comentario.class);

                        if (comentario.getUsuarioUID().equals(usuarioUID)) {
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

    public void darLikeAUnComentario(Integer idNoticia, Integer idComentario, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("like").child("usuarios");
        List<String> uidUsuario = new ArrayList<>();
        uidUsuario.add(user.getUid());
        databaseReference.setValue(uidUsuario);
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("like").child("cantLikes");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer cantLikes = dataSnapshot.getValue(Integer.class);
                databaseReference.setValue(cantLikes + 1);
                escuchadorDelControlador.finish(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error();
            }
        });
    }

    public void darDisLikeAUnComentario(Integer idNoticia, Integer idComentario, final ResultListener<Boolean> escuchadorDelControlador) {
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("disLike").child("usuarios");
        List<String> uidUsuario = new ArrayList<>();
        uidUsuario.add(user.getUid());
        databaseReference.setValue(uidUsuario);
        databaseReference = firebaseDatabase.getReference().child(Helper.REFERENCIA_COMENTARIOS).child(idNoticia.toString()).child(idComentario.toString()).child("disLike").child("cantDisLikes");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer cantDisLikes = dataSnapshot.getValue(Integer.class);
                databaseReference.setValue(cantDisLikes + 1);
                escuchadorDelControlador.finish(true);
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
