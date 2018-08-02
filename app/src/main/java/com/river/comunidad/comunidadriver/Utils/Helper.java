package com.river.comunidad.comunidadriver.Utils;
/*
  Creado por Camilo 05/06/2018.
 */


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.river.comunidad.comunidadriver.Model.Model.Excerpt;
import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static final String urlBase = "http://www.comunidadriver.com/wp-json/";

    public static final String urlAPINotification = "http://www.palabras.com.ar/pnfw/register/";

    public static final int BREVES = 44;

    public static final int AMERICA = 53;

    public static final int ANDROID = 10;

    public static final int ARQUITECTURA = 57;

    public static final int ARTESESCENICAS = 49;

    public static final int AUDIOVISUALES = 33;

    public static final int BALANCESYPERSPECTIVAS = 50;

    public static final int BALLET = 35;

    public static final int CINEYSERIES = 3;

    public static final int AGENDA = 34;

    public static final String REFERENCIA_CONTENIDO_FAVORITO = "Notas Favoritas";


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



    public static List<Noticia> eliminarNoticiasBrevesYAgenda(List<Noticia> listaDeNoticias) {
        List<Noticia> listaDeNoticiasARemover = new ArrayList<>();


        for (Integer i = 0; i < listaDeNoticias.size(); i++) {

            for (Integer j = 0; j < listaDeNoticias.get(i).getCategories().size(); j++) {

                if (listaDeNoticias.get(i).getCategories().get(j).equals(Helper.AGENDA) || listaDeNoticias.get(i).getCategories().get(j).equals(Helper.BREVES)) {

                    listaDeNoticiasARemover.add(listaDeNoticias.get(i));

                }
            }
        }

        listaDeNoticias.removeAll(listaDeNoticiasARemover);

        return listaDeNoticias;
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




