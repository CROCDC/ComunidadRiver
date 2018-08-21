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

import com.river.comunidad.comunidadriver.Model.Models.ListadoDeNoticias;
import com.river.comunidad.comunidadriver.Model.Models.Noticia;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class ListaDeNoticiasEnVerticalAdapter extends RecyclerView.Adapter {

    private List<Noticia> listaDeNoticias;
    private Context context;
    Integer cantidad = 0;

    private NotificadorHaciaNoticiasActivity notificadorHaciaNoticiasActivity;

    public ListaDeNoticiasEnVerticalAdapter(NotificadorHaciaNoticiasActivity notificadorHaciaNoticiasActivity) {
        listaDeNoticias = new ArrayList<>();
        this.notificadorHaciaNoticiasActivity = notificadorHaciaNoticiasActivity;
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

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_noticia_vertical, parent, false);

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
        private TextView webViewTituloDeLaNoticia;
        private TextView webViewCopeteDeLaNoticia;


        public NoticiasViewHolder(View itemView) {
            super(itemView);

            imageViewDeLaNoticia = itemView.findViewById(R.id.imageViewDeLaNoticia_celdanoticiavertical);
            webViewTituloDeLaNoticia = itemView.findViewById(R.id.textViewTituloDeLaNoticia_celdanoticiavertical);
            webViewCopeteDeLaNoticia = itemView.findViewById(R.id.textViewCopeteDeLaNoticia_celdanoticiavertical);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorHaciaNoticiasActivity.notificarANoticiasActivity(new ListadoDeNoticias(listaDeNoticias), getAdapterPosition());
                }
            });

        }

        public void cargarNoticia(Noticia noticia) {

            try {
                webViewTituloDeLaNoticia.setText(Html.fromHtml(noticia.getTitle().getRendered()));
                webViewCopeteDeLaNoticia.setText(Html.fromHtml(noticia.getExcerpt().getRendered()));
                Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getMedium_Large().getSource_url());


                /*webViewTituloDeLaNoticia.loadData(noticia.getTitle().getRendered(), "text/html", "UTF-8");
                webViewTituloDeLaNoticia.setBackgroundColor(Color.parseColor("#e02b20"));*/

                /*WebSettings webSettings = webViewTituloDeLaNoticia.getSettings();
                webSettings.setDefaultFontSize(20);
                webViewTituloDeLaNoticia.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);*/


                /*webViewCopeteDeLaNoticia.loadData(noticia.getExcerpt().getRendered(), "text/html", "UTF-8");*/
            } catch (Exception e) {
                try {
                    Helper.cargarImagenes(imageViewDeLaNoticia,context,noticia.getEmbedded().getListaDeImagenes().get(0).getsource_url());

                }catch (Exception ex){
                    Helper.cargarImagenes(imageViewDeLaNoticia, context, noticia.getEmbedded().getListaDeImagenes().get(0).getMedia_details().getSizes().getThumbnail().getSource_url());

                }

            }
        }
    }

    public interface NotificadorHaciaNoticiasActivity {
        //envio la lista y la posicion para identificar que noticia fue tocada
        public void notificarANoticiasActivity(ListadoDeNoticias listadoDeNoticias, Integer posicionActual);
    }
}
