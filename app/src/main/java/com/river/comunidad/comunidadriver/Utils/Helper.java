package com.river.comunidad.comunidadriver.Utils;
/*
  Creado por Camilo 05/06/2018.
 */


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.river.comunidad.comunidadriver.Model.Models.Excerpt;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.List;

public class Helper {
    public static final String urlBase = "http://www.comunidadriver.com/wp-json/";
    public static final String urlAPINotification = "http://www.comunidadriver.com/pnfw/register/";


    public static final String REFERENCIA_CONTENIDO_FAVORITO = "Noticias Favoritas";
    public static final String REFERENCIA_COMENTARIOS ="Comentarios";

    public static final int CATEGORIA_DEPORTE_Y_SOCIEDAD = 30;
    public static final int CATEGORIA_EFEMÉRIDES = 46;
    public static final int CATEGORIA_NOTICIAS = 2;
    public static final int CATEGORIA_NUESTRO_CLUB = 6;
    public static final int CATEGORIA_POR_LOS_QUINCHOS = 45;
    public static final int CATEGORIA_RIVER_EN_FOTOS = 3;


    //Removedor de etiquetas HMTL
    public static String eliminarEtiquetasHTML(String html) {
        return Jsoup.parse(html).text();
    }


    public static void acomodarPreviewDeNotasPorLista(List<Noticia> listaDeNoticia) {
        for (Integer i = 0; i < listaDeNoticia.size(); i++) {

            Excerpt excerpt = listaDeNoticia.get(i).getExcerpt();

            try {
                excerpt.setRendered(excerpt.getRendered().replace(excerpt.getRendered().substring(0, 3), ""));

            }catch (Exception e){
                excerpt.setRendered("no disponible");
            }


        }
    }

    public static void acomodarPreviewDeNotasEnSingular(Noticia noticia) {

        Excerpt excerpt = noticia.getExcerpt();

        excerpt.setRendered(excerpt.getRendered().replace(excerpt.getRendered().substring(0, 3), ""));

        Integer tamañoDeLaPreview = noticia.getExcerpt().getRendered().length();
        excerpt.setRendered(excerpt.getRendered().replace(excerpt.getRendered().substring(tamañoDeLaPreview - 15, tamañoDeLaPreview), "..."));
    }






    public static void cargarImagenes(ImageView imageView, Context context, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }


    public static void cargarImagenesAgenda(ImageView imageView, Context context, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }


}




