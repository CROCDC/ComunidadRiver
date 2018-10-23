package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class PosteoConVideo extends Posteo {
    private String elementoMultimedial;


    public PosteoConVideo(){

    }

    public PosteoConVideo(String nombreDeUsuario, long fechaDePublicacion, Integer tipoDePosteo, String imagenDelUsuario, String usuarioUID, String texto, Like like, DisLike disLike, List<Comentario> comentarios, String elementoMultimedial) {
        super(nombreDeUsuario, fechaDePublicacion, tipoDePosteo, imagenDelUsuario, usuarioUID, texto, like, disLike, comentarios);
        this.elementoMultimedial = elementoMultimedial;
    }

    @Override
    public String getElementoMultimedial() {
        return elementoMultimedial;
    }

    @Override
    public void setElementoMultimedial(String elementoMultimedial) {
        this.elementoMultimedial = elementoMultimedial;
    }
}
