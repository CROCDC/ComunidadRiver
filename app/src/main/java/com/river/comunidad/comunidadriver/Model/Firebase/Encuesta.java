package com.river.comunidad.comunidadriver.Model.Firebase;


import java.io.Serializable;
import java.util.List;

public class Encuesta implements Serializable {
    private Integer idEncuesta;
    private List<Respuesta> listaDeRespuetas;
    private List<Pregunta> listaDePreguntas;

}
