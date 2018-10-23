package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class PosteoConTexto extends Posteo {

    public PosteoConTexto(){

    }

    public PosteoConTexto(String nombreDeUsuario, long fechaDePublicacion, Integer tipoDePosteo, String imagenDelUsuario, String usuarioUID, String texto, Like like, DisLike disLike, List<Comentario> comentarios) {
        super(nombreDeUsuario, fechaDePublicacion, tipoDePosteo, imagenDelUsuario, usuarioUID, texto, like, disLike, comentarios);
    }
}
