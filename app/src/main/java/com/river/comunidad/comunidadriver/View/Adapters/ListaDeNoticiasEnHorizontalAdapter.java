package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class ListaDeNoticiasEnHorizontalAdapter extends RecyclerView.Adapter {

    private List<Noticia> listaDeNoticias;
    private Context context;

    public ListaDeNoticiasEnHorizontalAdapter() {
        listaDeNoticias = new ArrayList<>();
    }

    public void setListaDeNoticias(List<Noticia> listaDeNoticias) {
        this.listaDeNoticias = listaDeNoticias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_noticias_horizontal, parent, false);

        NoticiasViewHolder noticiasViewHolder = new NoticiasViewHolder(viewDeLaCelda);

        return noticiasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Noticia noticia = listaDeNoticias.get(i);

        NoticiasViewHolder noticiasViewHolder = (NoticiasViewHolder) viewHolder;

        noticiasViewHolder.cargarNoticia(noticia);

    }

    @Override
    public int getItemCount() {
        return listaDeNoticias.size();
    }

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewDeLaNoticia;
        private TextView webViewTituloDeLaNoticia;
        private TextView webViewCopeteDeLaNoticia;


        public NoticiasViewHolder(View itemView) {
            super(itemView);

            imageViewDeLaNoticia = itemView.findViewById(R.id.imageViewDeLaNoticia_celdanoticiahorizontal);
            webViewTituloDeLaNoticia = itemView.findViewById(R.id.textViewTituloDeLaNoticia_celdanoticiahorizontal);
            webViewCopeteDeLaNoticia = itemView.findViewById(R.id.textViewCopeteDeLaNoticias_celdanoticiahorizontal);


        }

        public void cargarNoticia(Noticia noticia) {

            try {
                webViewTituloDeLaNoticia.setText(Html.fromHtml(noticia.getTitle().getRendered()));
                webViewCopeteDeLaNoticia.setText(Html.fromHtml(noticia.getExcerpt().getRendered()));
                Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium_Large().getSource_url());


            } catch (Exception e) {
                try {
                    Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getsource_url());

                } catch (Exception ex) {
                    Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getThumbnail().getSource_url());

                }

            }
        }
    }
}
