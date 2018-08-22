package com.river.comunidad.comunidadriver.Model.Models;

public class Respuesta {
    private String usuario;
    private String usuarioUID;
    private String urlImagen;
    private Integer likes;
    private Integer disLikes;
    private String texto;
    private String fechaDePublicacion;

    public Respuesta() {
    }

    public Respuesta(String usuario, String usuarioUID, String urlImagen, Integer likes, Integer disLikes, String texto, String fechaDePublicacion) {
        this.usuario = usuario;
        this.usuarioUID = usuarioUID;
        this.urlImagen = urlImagen;
        this.likes = likes;
        this.disLikes = disLikes;
        this.texto = texto;
        this.fechaDePublicacion = fechaDePublicacion;
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

    public Integer getLikes() {
        return likes;
    }

    public Integer getDisLikes() {
        return disLikes;
    }

    public String getTexto() {
        return texto;
    }

    public String getFechaDePublicacion() {
        return fechaDePublicacion;
    }
}
