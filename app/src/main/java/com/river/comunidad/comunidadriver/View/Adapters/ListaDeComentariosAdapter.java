package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.river.comunidad.comunidadriver.Model.Models.Comentario;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.Helper;
import com.river.comunidad.comunidadriver.Utils.MiRelojDeArena;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaDeComentariosAdapter extends RecyclerView.Adapter {

    private List<Comentario> listaDeComentarios;
    private Context context;
    private NotificadorHaciaImplementadorDeComentariosAdapter notificadorHaciaImplementadorDeComentariosAdapter;

    public ListaDeComentariosAdapter(NotificadorHaciaImplementadorDeComentariosAdapter notificadorHaciaImplementadorDeComentariosAdapter) {
        listaDeComentarios = new ArrayList<>();
        this.notificadorHaciaImplementadorDeComentariosAdapter = notificadorHaciaImplementadorDeComentariosAdapter;
    }

    public void setListaDeComentarios(List<Comentario> listaDeComentarios) {
        this.listaDeComentarios = listaDeComentarios;
        notifyDataSetChanged();
    }


    public List<Comentario> getListaDeComentarios() {
        return listaDeComentarios;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_comentario, viewGroup, false);

        ComentariosViewHolder comentariosViewHolder = new ComentariosViewHolder(viewDeLaCelda);

        return comentariosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Comentario comentario = listaDeComentarios.get(i);

        ComentariosViewHolder comentariosViewHolder = (ComentariosViewHolder) viewHolder;

        comentariosViewHolder.cargarComentario(comentario);


    }

    @Override
    public int getItemCount() {
        return listaDeComentarios.size();
    }

    public class ComentariosViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewUsuario;
        private TextView textViewNombreDeUsuario;
        private TextView textViewFechaDePublicacionDelComentario;
        private TextView textViewContendioDelComentario;
        private ImageView imageViewButtonLike;
        private ImageView imageViewButtonDisLike;
        private TextView textViewCantidadDeLikes;
        private TextView textViewCantidadDeDisLikes;
        private RecyclerView recyclerViewListaDeRespuestas;

        public ComentariosViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageViewUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdacomentario);
            textViewNombreDeUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdacomentario);
            textViewFechaDePublicacionDelComentario = itemView.findViewById(R.id.textViewFechaDePublicacionDelComentario_celdacomentario);
            textViewContendioDelComentario = itemView.findViewById(R.id.textViewContenidoDelComentario_celdacomentario);
            imageViewButtonLike = itemView.findViewById(R.id.imageViewButtonLike_celdacomentario);
            imageViewButtonDisLike = itemView.findViewById(R.id.imageViewButtonDisLike_celdacomentario);
            textViewCantidadDeLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdacomentario);
            textViewCantidadDeDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdacomentario);
            recyclerViewListaDeRespuestas = itemView.findViewById(R.id.recyclerViewListaDeRespuestas_celdacomentario);

        }


        public void cargarComentario(final Comentario comentario) {

            ListaDeRespuestasAdapter listaDeRespuestasAdapter = new ListaDeRespuestasAdapter(new ListaDeRespuestasAdapter.NotificarClick() {
                @Override
                public void notificar() {
                    notificadorHaciaImplementadorDeComentariosAdapter.notificar();
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                //UTILIZO ESTO PARA DESHABILITAR LA POSIBILIDAD DE SCROLLEAR EN EL RECYCLERVIEW
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            listaDeRespuestasAdapter.setListaDeRespuestas(comentario.getListaDeRespuestas());

            recyclerViewListaDeRespuestas.setLayoutManager(linearLayoutManager);

            recyclerViewListaDeRespuestas.setAdapter(listaDeRespuestasAdapter);

            Helper.cargarImagenes(circleImageViewUsuario, context, comentario.getUrlImagen());
            textViewNombreDeUsuario.setText(comentario.getUsuario());
            textViewContendioDelComentario.setText(comentario.getTexto());
            textViewCantidadDeLikes.setText(comentario.getLike().getCantLikes().toString());
            textViewCantidadDeDisLikes.setText(comentario.getDisLike().getCantDisLikes().toString());
            textViewFechaDePublicacionDelComentario.setText(MiRelojDeArena.getTimeAgo(comentario.getFechaDePublicacion()));

            imageViewButtonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorHaciaImplementadorDeComentariosAdapter.noticicarTouchLikeButton(getAdapterPosition() + 1);
                }
            });

            imageViewButtonDisLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorHaciaImplementadorDeComentariosAdapter.notificarTouchDisLikeButton(getAdapterPosition() + 1);
                }
            });

        }
    }

    public interface NotificadorHaciaImplementadorDeComentariosAdapter {
        public void notificar();

        public void noticicarTouchLikeButton(Integer idComentario);

        public void notificarTouchDisLikeButton(Integer idComentario);
    }

}
