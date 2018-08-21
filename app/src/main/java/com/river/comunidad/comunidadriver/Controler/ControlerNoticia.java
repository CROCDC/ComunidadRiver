package com.river.comunidad.comunidadriver.Controler;

import android.content.Context;

import com.river.comunidad.comunidadriver.DAO.DAONoticiaRetrofit;

import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;


import java.util.List;

public class ControlerNoticia {

    public static final int PaginaInicial = 1;
    public static final int PaginasTotales = 10;
    public static final Boolean paginar = true;
    private Integer paginaActual;
    private Context context;


    public ControlerNoticia(Context context) {
        paginaActual = 1;
        this.context = context;

    }

    public void pedirListaDeNoticias(Integer cantResultado,final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        new DAONoticiaRetrofit(context).pedirListaDeNoticias(cantResultado,new ResultListener<List<Noticia>>() {

            @Override
            public void finish(final List<Noticia> resultado) {

                Helper.acomodarPreviewDeNotasPorLista(resultado);

                escuchadorDeLaVista.finish(resultado);

                setPaginaActual(paginaActual += 1);

            }
        }, paginaActual);
    }
    public void pedirListaDeNoticiasPaginado(Integer cantResultado,Integer paginaSolicitada,final ResultListener<List<Noticia>> escuchadorDeLaVista){
        new DAONoticiaRetrofit(context).pedirListaDeNoticias(cantResultado, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        },paginaSolicitada);
    }

    public void pedirListaDeNoticiassDeLaAgenda(Integer pagina, Integer tamaño, final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        new DAONoticiaRetrofit(context).pedirListaDeNoticiasDeLaAgenda(pagina, tamaño, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void pedirListaDeNoticiasPorCategoria(final ResultListener<List<Noticia>> escuchadorDeLaVista, Integer categoria) {
        new DAONoticiaRetrofit(context).pedirListaDeNoticiasPorCategoria(new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                if (!(resultado == null)) {
                    Helper.acomodarPreviewDeNotasPorLista(resultado);
                    escuchadorDeLaVista.finish(resultado);
                    setPaginaActual(paginaActual += 1);
                }

            }
        }, paginaActual, categoria);
    }

    public void pedirListaDeNoticiasDeLaBusqueda(String busqueda, final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        new DAONoticiaRetrofit(context).pedirListaDeNoticiasPorBusqueda(busqueda, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                Helper.acomodarPreviewDeNotasPorLista(resultado);
                escuchadorDeLaVista.finish(resultado);
            }
        });

    }

    public void pedirNoticiasPorID(Integer id, final ResultListener<Noticia> escuchadorDeLaVista) {
        new DAONoticiaRetrofit(context).pedirNoticiaPorID(id, new ResultListener<Noticia>() {
            @Override
            public void finish(Noticia resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }
}
