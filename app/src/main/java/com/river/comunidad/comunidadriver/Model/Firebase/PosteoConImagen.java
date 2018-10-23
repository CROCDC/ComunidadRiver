package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class PosteoConImagen extends Posteo {

    public PosteoConImagen(){

    }

    public PosteoConImagen(String nombreDeUsuario, long fechaDePublicacion, Integer tipoDePosteo, String imagenDelUsuario, String usuarioUID, String texto, Like like, DisLike disLike, List<Comentario> comentarios, String elementoMultimedial) {
        super(nombreDeUsuario, fechaDePublicacion, tipoDePosteo, imagenDelUsuario, usuarioUID, texto, like, disLike, comentarios);
        this.elementoMultimedial = elementoMultimedial;
    }

    private String elementoMultimedial;

    public String getElementoMultimedial() {
        return elementoMultimedial;
    }

    public void setElementoMultimedial(String elementoMultimedial) {
        this.elementoMultimedial = elementoMultimedial;
    }
}
