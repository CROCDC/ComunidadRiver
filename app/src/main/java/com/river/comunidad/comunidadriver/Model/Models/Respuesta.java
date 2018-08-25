package com.river.comunidad.comunidadriver.Model.Models;

public class Respuesta {
    private Integer idNoticia;
    private Integer idComentario;
    private String usuario;
    private String usuarioUID;
    private String urlImagen;
    private Like like;
    private DisLike disLike;
    private String texto;
    private long fechaDePublicacion;

    public Respuesta() {
    }


    public Respuesta(Integer idNoticia, Integer idComentario, String usuario, String usuarioUID, String urlImagen, Like like, DisLike disLike, String texto, long fechaDePublicacion) {
        this.idNoticia = idNoticia;
        this.idComentario = idComentario;
        this.usuario = usuario;
        this.usuarioUID = usuarioUID;
        this.urlImagen = urlImagen;
        this.like = like;
        this.disLike = disLike;
        this.texto = texto;
        this.fechaDePublicacion = fechaDePublicacion;
    }

    
    
    public Integer getIdNoticia() {
        return idNoticia;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public Like getLike() {
        return like;
    }

    public DisLike getDisLike() {
        return disLike;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getUsuarioUID() {
        return usuarioUID;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getTexto() {
        return texto;
    }

    public long getFechaDePublicacion() {
        return fechaDePublicacion;
    }
}
