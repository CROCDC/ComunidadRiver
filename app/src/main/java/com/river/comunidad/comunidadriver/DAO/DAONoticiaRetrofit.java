package com.river.comunidad.comunidadriver.DAO;
/*
  Creado por Camilo 05/06/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.Utils.ServiceNoticia;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAONoticiaRetrofit{
    private Retrofit retrofit;
    private Context context;
    private ServiceNoticia serviceNoticia;


    public DAONoticiaRetrofit(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Helper.urlBase)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();

        this.context = context;
    }


    public void pedirListaDeNoticias(Integer cantResultados, final ResultListener<List<Noticia>> escuchadorDelControlador, final Integer pagina) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);
        Call<List<Noticia>> llamada = serviceNoticia.pedirListaDeNoticias(pagina, cantResultados);

        llamada.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.body() != null){
                    escuchadorDelControlador.finish(response.body());
                }else {
                    aFalladoLaConexion();
                }

            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {

                if (pagina == 1){
                    aFalladoLaConexion();
                }else {
                    FancyToast.makeText(context, "La conexion a fallado", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        });
    }

    public void pedirListaDeNoticiasPorCategoria(final ResultListener<List<Noticia>> escuchadorDelControlador, Integer pagina, Integer categoria) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);

        Call<List<Noticia>> llamada = serviceNoticia.pedirListaDeNoticiasPorCategoria(categoria, pagina, 5);

        llamada.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                escuchadorDelControlador.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                aFalladoLaConexion();
            }
        });
    }

    public void pedirListaDeNoticiasDeLaAgenda(Integer pagina, Integer tamaño, final ResultListener<List<Noticia>> escuchadorDelControlador) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);
        Call<List<Noticia>> llamada = serviceNoticia.pedirListaDeNoticiasPorCategoria(34, pagina, tamaño);
        llamada.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                escuchadorDelControlador.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                aFalladoLaConexion();
            }
        });
    }

    public void pedirListaDeNoticiasPorBusqueda(String busqueda, final ResultListener<List<Noticia>> escuchadorDelControlador) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);
        Call<List<Noticia>> llamada = serviceNoticia.pedirListaDeNoticiasPorBusqueda(busqueda);

        llamada.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                escuchadorDelControlador.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                aFalladoLaConexion();
            }
        });
    }

    public void pedirNoticiaPorID(Integer id, final ResultListener<Noticia> esuchadorDelControlador) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);
        Call<Noticia> llamada = serviceNoticia.pedirUnaNoticiaPorID(id);

        llamada.enqueue(new Callback<Noticia>() {
            @Override
            public void onResponse(Call<Noticia> call, Response<Noticia> response) {
                esuchadorDelControlador.finish(response.body());
            }

            @Override
            public void onFailure(Call<Noticia> call, Throwable t) {
                aFalladoLaConexion();
            }
        });
    }

    public void aFalladoLaConexion() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FancyToast.makeText(context, "La conexion a fallado", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                ((Activity) context).onBackPressed();
            }
        }, 1000);
    }
}
