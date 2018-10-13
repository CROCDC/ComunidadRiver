package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class Comentario {
    private Integer idNoticia;
    private String usuario;
    private String usuarioUID;
    private String urlImagen;
    private Like like;
    private DisLike disLike;
    private String texto;
    private long fechaDePublicacion;
    private List<Respuesta> listaDeRespuestas;
    private String idComentario;



    public Comentario() {
    }

    public Comentario(Integer idNoticia, String usuarioUID, String usuario, String urlImagen, Like like, DisLike disLike, String texto, long fechaDePublicacion, List<Respuesta> listaDeRespuestas, String idComentario) {
        this.idNoticia = idNoticia;
        this.usuario = usuario;
        this.usuarioUID = usuarioUID;
        this.urlImagen = urlImagen;
        this.like = like;
        this.disLike = disLike;
        this.texto = texto;
        this.fechaDePublicacion = fechaDePublicacion;
        this.listaDeRespuestas = listaDeRespuestas;
        this.idComentario = idComentario;
    }

    public void setLike(Like like) {
        this.like = like;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public Integer getIdNoticia() {
        return idNoticia;
    }

    public String getUsuarioUID() {
        return usuarioUID;
    }

    public String getTexto() {
        return texto;
    }

    public List<Respuesta> getListaDeRespuestas() {
        return listaDeRespuestas;
    }

    public long getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public String getIdComentario() {
        return idComentario;
    }

    public void setListaDeRespuestas(List<Respuesta> listaDeRespuestas) {
        this.listaDeRespuestas = listaDeRespuestas;
    }
}



