package com.river.comunidad.comunidadriver.Controller;

import android.annotation.SuppressLint;

import com.river.comunidad.comunidadriver.DAO.DAOPosteosFirebase;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConImagen;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConTexto;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConVideo;
import com.river.comunidad.comunidadriver.Utils.ResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerPosteosFirebase {
    private DAOPosteosFirebase daoPosteosFirebase;

    public ControllerPosteosFirebase() {
        daoPosteosFirebase = new DAOPosteosFirebase();
    }

    public void publicarPosteo(Posteo posteo, final ResultListener<Boolean> escuchadorDeLaVista) {
        daoPosteosFirebase.publicarPosteo(posteo, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {

                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void obtenerPosteosDeLosUsuarios(final ResultListener<List<Posteo>> escuchadorDeLaVista) {
        daoPosteosFirebase.obtenerPosteosDeLosUsuarios(new ResultListener<List<Posteo>>() {
            @SuppressLint("NewApi")
            @Override
            public void finish(List<Posteo> resultado) {
                List<Posteo> listaDePosteos = new ArrayList<>();

                resultado.forEach(i -> {

                    switch (i.getTipoDePosteo()) {
                        case 1:
                            PosteoConImagen posteoConImagen = new PosteoConImagen(
                                    i.getNombreDeUsuario(),
                                    i.getFechaDePublicacion(),
                                    i.getTipoDePosteo(),
                                    i.getImagenDelUsuario(),
                                    i.getUsuarioUID(),
                                    i.getTexto(),
                                    i.getLike(),
                                    i.getDisLike(),
                                    i.getComentarios(),
                                    i.getElementoMultimedial());
                            listaDePosteos.add(posteoConImagen);
                            break;
                        case 2:
                            PosteoConVideo posteoConVideo = new PosteoConVideo(
                                    i.getNombreDeUsuario(),
                                    i.getFechaDePublicacion(),
                                    i.getTipoDePosteo(),
                                    i.getImagenDelUsuario(),
                                    i.getUsuarioUID(),
                                    i.getTexto(),
                                    i.getLike(),
                                    i.getDisLike(),
                                    i.getComentarios(),
                                    i.getElementoMultimedial());

                            listaDePosteos.add(posteoConVideo);
                            break;
                        case 3:
                            PosteoConTexto posteoConTexto = new PosteoConTexto(
                                    i.getNombreDeUsuario(),
                                    i.getFechaDePublicacion(),
                                    i.getTipoDePosteo(),
                                    i.getImagenDelUsuario(),
                                    i.getUsuarioUID(),
                                    i.getTexto(),
                                    i.getLike(),
                                    i.getDisLike(),
                                    i.getComentarios()
                            );

                            listaDePosteos.add(posteoConTexto);
                            break;
                    }

                });


                Collections.reverse(listaDePosteos);
                escuchadorDeLaVista.finish(listaDePosteos);
            }
        });
    }

    public void subirFileDelPosteoAFireStorage(File file, final ResultListener<String> escuchadorDeLaVista, final ResultListener<Integer> escuchadorDelProgreso) {
        daoPosteosFirebase.subirImagenDelPosteoAFireStorage(file, new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        }, new ResultListener<Integer>() {
            @Override
            public void finish(Integer resultado) {
                escuchadorDelProgreso.finish(resultado);
            }
        });
    }
}
