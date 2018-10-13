package com.river.comunidad.comunidadriver.Model.Firebase;

import java.util.List;

public class Posteo {
    private String nombreDeUsuario;
    private String fechaDePublicacion;
    private String elementoMultimedial;
    private String imagenDelUsuario;
    private String usuarioUID;
    private String titulo;
    private String texto;
    private Like like;
    private DisLike disLike;
    private List<Comentario> comentarios;



    public Posteo() {
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(String fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public String getElementoMultimedial() {
        return elementoMultimedial;
    }

    public void setElementoMultimedial(String elementoMultimedial) {
        this.elementoMultimedial = elementoMultimedial;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
