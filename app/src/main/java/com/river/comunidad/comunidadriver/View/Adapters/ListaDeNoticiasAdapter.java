package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.river.comunidad.comunidadriver.Model.Model.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class ListaDeNoticiasAdapter extends RecyclerView.Adapter {

    private List<Noticia> listaDeNoticias;
    private Context context;

    public ListaDeNoticiasAdapter() {
    listaDeNoticias = new ArrayList<>();
    }

    public void setListaDeNoticias(List<Noticia> listaDeNoticias) {
        this.listaDeNoticias = listaDeNoticias;
        notifyDataSetChanged();
    }

    public void agregarNoticiasALaLista(List<Noticia> listaDeNotasAgregar) {
        this.listaDeNoticias.addAll(listaDeNotasAgregar);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_noticia,parent,false);

        NoticiasViewHolder noticiasViewHolder = new NoticiasViewHolder(viewDeLaCelda);

        return noticiasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Noticia noticia = listaDeNoticias.get(position);

        NoticiasViewHolder noticiasViewHolder = (NoticiasViewHolder) holder;

        noticiasViewHolder.cargarNoticia(noticia);
    }

    @Override
    public int getItemCount() {
        return listaDeNoticias.size();
    }

    private class NoticiasViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewDeLaNoticia;
        private WebView webViewTituloDeLaNoticia;
        private WebView webViewCopeteDeLaNoticia;


        public NoticiasViewHolder(View itemView) {
            super(itemView);

            imageViewDeLaNoticia = itemView.findViewById(R.id.imageViewDeLaNoticia_celdanoticia);
            webViewTituloDeLaNoticia = itemView.findViewById(R.id.textViewTituloDeLaNoticia_celdanoticia);
            webViewCopeteDeLaNoticia = itemView.findViewById(R.id.textViewCopeteDeLaNoticia_celdanoticia);

        }

        public void cargarNoticia(Noticia noticia) {
            try {

                Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium().getSource_url());
                webViewTituloDeLaNoticia.loadData(noticia.getTitle().getRendered(),"text/html","UTF-8");
                webViewTituloDeLaNoticia.setBackgroundColor(Color.parseColor("#e02b20"));

                WebSettings webSettings = webViewTituloDeLaNoticia.getSettings();
                webSettings.setDefaultFontSize(20);
                webViewTituloDeLaNoticia.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


                webViewCopeteDeLaNoticia.loadData(noticia.getExcerpt().getRendered(),"text/html","UTF-8");
            } catch (Exception e) {

            }
        }
    }
}
