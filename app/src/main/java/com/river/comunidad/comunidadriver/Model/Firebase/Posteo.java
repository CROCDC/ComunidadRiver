package com.river.comunidad.comunidadriver.Model.Firebase;

import java.io.Serializable;
import java.util.List;

public class Posteo implements Serializable {
    private String nombreDeUsuario;
    private long fechaDePublicacion;
    private Integer tipoDePosteo;
    private String imagenDelUsuario;
    private String usuarioUID;
    private String elementoMultimedial;
    private String texto;
    private Like like;
    private DisLike disLike;
    private List<Comentario> comentarios;



    public Posteo() {
    }

    public Posteo(String nombreDeUsuario, long fechaDePublicacion, Integer tipoDePosteo, String imagenDelUsuario, String usuarioUID, String texto, Like like, DisLike disLike, List<Comentario> comentarios) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.fechaDePublicacion = fechaDePublicacion;
        this.tipoDePosteo = tipoDePosteo;
        this.imagenDelUsuario = imagenDelUsuario;
        this.usuarioUID = usuarioUID;
        this.texto = texto;
        this.like = like;
        this.disLike = disLike;
        this.comentarios = comentarios;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }


    public long getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(long fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public Integer getTipoDePosteo() {
        return tipoDePosteo;
    }

    public void setTipoDePosteo(Integer tipoDePosteo) {
        this.tipoDePosteo = tipoDePosteo;
    }


    public String getImagenDelUsuario() {
        return imagenDelUsuario;
    }

    public void setImagenDelUsuario(String imagenDelUsuario) {
        this.imagenDelUsuario = imagenDelUsuario;
    }

    public String getUsuarioUID() {
        return usuarioUID;
    }

    public void setUsuarioUID(String usuarioUID) {
        this.usuarioUID = usuarioUID;
    }



    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    public DisLike getDisLike() {
        return disLike;
    }

    public void setDisLike(DisLike disLike) {
        this.disLike = disLike;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getElementoMultimedial() {
        return elementoMultimedial;
    }

    public void setElementoMultimedial(String elementoMultimedial) {
        this.elementoMultimedial = elementoMultimedial;
    }
}
