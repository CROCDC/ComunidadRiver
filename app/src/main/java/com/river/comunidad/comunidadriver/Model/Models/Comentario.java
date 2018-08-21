package com.river.comunidad.comunidadriver.Model.Models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class Comentario {
    private Integer idNoticia;
    private String usuario;
    private String usuarioUID;
    private String urlImagen;
    private Integer likes;
    private Integer disLikes;
    private String texto;
    private String fechaDePublicacion;

    public Comentario() {

    }

    public Comentario(String usuarioUID, Integer idNoticia, String usuario, String urlImagen, Integer likes, Integer disLikes, String texto, String fechaDePublicacion) {
        this.usuarioUID = usuarioUID;
        this.idNoticia = idNoticia;
        this.usuario = usuario;
        this.urlImagen = urlImagen;
        this.likes = likes;
        this.disLikes = disLikes;
        this.texto = texto;
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public Integer getDisLikes() {
        return disLikes;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Integer getIdNoticia() {
        return idNoticia;
    }

    public String getUsuarioUID() {
        return usuarioUID;
    }

    public Integer getLikes() {
        return likes;
    }

    public String getTexto() {
        return texto;
    }

    public String getFechaDePublicacion() {
        return fechaDePublicacion;
    }
}

