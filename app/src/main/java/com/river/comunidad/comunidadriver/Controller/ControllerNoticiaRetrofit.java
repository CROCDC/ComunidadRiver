package com.river.comunidad.comunidadriver.Controller;

import android.content.Context;

import com.river.comunidad.comunidadriver.DAO.DAONoticiaRetrofit;

import com.river.comunidad.comunidadriver.Model.API_REST.Noticia;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;


import java.util.List;

public class ControllerNoticiaRetrofit {

    public static final int PaginaInicial = 1;
    public static final int PaginasTotales = 10;
    public static final Boolean paginar = true;
    private Integer paginaActual;
    private Context context;
    private DAONoticiaRetrofit daoNoticiaRetrofit;


    public ControllerNoticiaRetrofit(Context context) {
        paginaActual = 1;
        this.context = context;
        daoNoticiaRetrofit = new DAONoticiaRetrofit(context);

    }

    public void pedirListaDeNoticias(Integer cantResultado, final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        daoNoticiaRetrofit.pedirListaDeNoticias(cantResultado, new ResultListener<List<Noticia>>() {

            @Override
            public void finish(final List<Noticia> resultado) {

                Helper.acomodarPreviewDeNotasPorLista(resultado);

                escuchadorDeLaVista.finish(resultado);

                setPaginaActual(paginaActual += 1);

            }
        }, paginaActual);
    }

    public void pedirListaDeNoticiasPaginado(Integer cantResultado, Integer paginaSolicitada, final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        daoNoticiaRetrofit.pedirListaDeNoticias(cantResultado, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        }, paginaSolicitada);
    }

    public void pedirListaDeNoticiassDeLaAgenda(Integer pagina, Integer tamaño, final ResultListener<List<Noticia>> escuchadorDeLaVista) {
        daoNoticiaRetrofit.pedirListaDeNoticiasDeLaAgenda(pagina, tamaño, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        });
    }

    public void pedirListaDeNoticiasPorCategoria(final ResultListener<List<Noticia>> escuchadorDeLaVista, Integer categoria) {
        daoNoticiaRetrofit.pedirListaDeNoticiasPorCategoria(new ResultListener<List<Noticia>>() {
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
        daoNoticiaRetrofit.pedirListaDeNoticiasPorBusqueda(busqueda, new ResultListener<List<Noticia>>() {
            @Override
            public void finish(List<Noticia> resultado) {
                Helper.acomodarPreviewDeNotasPorLista(resultado);
                escuchadorDeLaVista.finish(resultado);
            }
        });

    }

    public void pedirNoticiaPorID(Integer id, final ResultListener<Noticia> escuchadorDeLaVista) {
        daoNoticiaRetrofit.pedirNoticiaPorID(id, new ResultListener<Noticia>() {
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
