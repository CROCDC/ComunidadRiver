package com.river.comunidad.comunidadriver.DAO;
/*
  Creado por Camilo 05/06/2018.
 */



import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.ResultListener;
import com.river.comunidad.comunidadriver.Utils.ServiceNoticia;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAONoticiaRetrofit {
    private Retrofit retrofit;
    private ServiceNoticia serviceNoticia;

    public DAONoticiaRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Helper.urlBase)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
    }


    public void pedirListaDeNoticias(final ResultListener<List<Noticia>> escuchadorDelControlador, Integer pagina) {
        serviceNoticia = retrofit.create(ServiceNoticia.class);
        Call<List<Noticia>> llamada = serviceNoticia.pedirListaDeNoticias(pagina);

        llamada.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                escuchadorDelControlador.finish(response.body());
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {

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

            }
        });
    }

}
