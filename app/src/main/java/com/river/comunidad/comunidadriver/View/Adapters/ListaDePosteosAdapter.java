package com.river.comunidad.comunidadriver.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.river.comunidad.comunidadriver.Model.Firebase.Posteo;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConImagen;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConTexto;
import com.river.comunidad.comunidadriver.Model.Firebase.PosteoConVideo;
import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.Utils.MiRelojDeArena;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaDePosteosAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Posteo> listaDePosteos;

    private static final Integer TIPO_CON_IMAGEN = 1;
    private static final Integer TIPO_CON_VIDEO = 2;
    private static final Integer TIPO_CON_TEXTO = 3;

    private Integer cantDislikes;
    private Integer cantLikes;

    public ListaDePosteosAdapter() {
        listaDePosteos = new ArrayList<>();

    }

    public void setListaDePosteos(List<Posteo> listaDePosteos) {
        this.listaDePosteos = listaDePosteos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (viewType == 1) {
            View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_posteo_con_imagen, viewGroup, false);

            return new PosteoConImagenViewHolder(viewDeLaCelda);
        } else if (viewType == 2) {
            View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_posteo_con_video, viewGroup, false);

            return new PosteoConVideoViewHolder(viewDeLaCelda);
        } else if (viewType == 3) {
            View viewDeLaCelda = layoutInflater.inflate(R.layout.celda_posteo_solo_texto, viewGroup, false);

            return new PosteoSoloTextoViewHolder(viewDeLaCelda);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof PosteoConImagenViewHolder) {
            PosteoConImagen posteo = (PosteoConImagen) listaDePosteos.get(i);

            PosteoConImagenViewHolder posteoConImagenViewHolder = (PosteoConImagenViewHolder) viewHolder;

            posteoConImagenViewHolder.cargarPosteo(posteo);
        } else if (viewHolder instanceof PosteoConVideoViewHolder) {
            PosteoConVideo posteo = (PosteoConVideo) listaDePosteos.get(i);

            PosteoConVideoViewHolder posteoConVideoViewHolder = (PosteoConVideoViewHolder) viewHolder;

            posteoConVideoViewHolder.cargarPosteo(posteo);
        } else if (viewHolder instanceof PosteoSoloTextoViewHolder) {
            PosteoConTexto posteo = (PosteoConTexto) listaDePosteos.get(i);

            PosteoSoloTextoViewHolder posteoSoloTextoViewHolder = (PosteoSoloTextoViewHolder) viewHolder;

            posteoSoloTextoViewHolder.cargarPosteo(posteo);

        }

    }


    @Override
    public int getItemViewType(int position) {
        Posteo posteo = listaDePosteos.get(position);

        if (posteo instanceof PosteoConImagen) {
            return TIPO_CON_IMAGEN;
        } else if (posteo instanceof PosteoConVideo) {
            return TIPO_CON_VIDEO;
        } else if (posteo instanceof PosteoConTexto) {
            return TIPO_CON_TEXTO;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        return listaDePosteos.size();
    }

    private class PosteoConImagenViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewDeUsuario;
        private TextView textViewNombreDelUsuario;
        private TextView textViewFechaDePublicacion;
        private TextView textViewDelPosteo;
        private ImageView imageViewDelPosteo;
        private ImageView imageViewButtonLike;
        private TextView textViewCantLikes;
        private ImageView imageViewButtonDisLikes;
        private TextView textViewCantDisLikes;
        private ImageView imageViewButtonResponder;

        public PosteoConImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewDeUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdaposteoconimagen);
            textViewNombreDelUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdaposteoconimagen);
            textViewFechaDePublicacion = itemView.findViewById(R.id.textViewFechaDePublicacionDelPosteo_celdaposteoconimagen);
            textViewDelPosteo = itemView.findViewById(R.id.textViewTextoDelPosteo_celdaposteoconimagen);
            imageViewDelPosteo = itemView.findViewById(R.id.imageViewDelPosteo_celdaposteoconimagen);
            imageViewButtonLike = itemView.findViewById(R.id.imageViewButtonLike_celdaposteoconimagen);
            textViewCantLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdaposteoconimagen);
            imageViewButtonDisLikes = itemView.findViewById(R.id.imageViewButtonDisLike_celdaposteoconimagen);
            textViewCantDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdaposteoconimagen);


        }

        public void cargarPosteo(PosteoConImagen posteo) {
            Glide.with(context)
                    .load(posteo.getImagenDelUsuario())
                    .into(circleImageViewDeUsuario);
            textViewNombreDelUsuario.setText(posteo.getNombreDeUsuario());

            textViewFechaDePublicacion.setText(MiRelojDeArena.getTimeAgo(posteo.getFechaDePublicacion()));
            textViewDelPosteo.setText(posteo.getTexto());

            Glide.with(context)
                    .load(posteo.getElementoMultimedial())
                    .into(imageViewDelPosteo);

            try {
                cantDislikes = posteo.getDisLike().getUsuarios().size();

            } catch (Exception e) {
                cantDislikes = 0;
            }

            try {
                cantLikes = posteo.getLike().getUsuarios().size();

            } catch (Exception e) {
                cantLikes = 0;
            }


            textViewCantLikes.setText(cantLikes.toString());
            textViewCantDisLikes.setText(cantDislikes.toString());
        }
    }

    private class PosteoConVideoViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewDeUsuario;
        private TextView textViewNombreDelUsuario;
        private TextView textViewFechaDePublicacion;
        private TextView textViewDelPosteo;
        private VideoView videoViewDelPosteo;
        private ImageView imageViewButtonLike;
        private TextView textViewCantLikes;
        private ImageView imageViewButtonDisLikes;
        private TextView textViewCantDisLikes;
        private ImageView imageViewButtonResponder;

        public PosteoConVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewDeUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdaposteoconimagen);
            textViewNombreDelUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdaposteoconimagen);
            textViewFechaDePublicacion = itemView.findViewById(R.id.textViewFechaDePublicacionDelPosteo_celdaposteoconimagen);
            textViewDelPosteo = itemView.findViewById(R.id.textViewTextoDelPosteo_celdaposteoconimagen);
            videoViewDelPosteo = itemView.findViewById(R.id.videoViewDelPosteo_celdaposteoconvideo);
            imageViewButtonLike = itemView.findViewById(R.id.imageViewButtonLike_celdaposteoconimagen);
            textViewCantLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdaposteoconimagen);
            imageViewButtonDisLikes = itemView.findViewById(R.id.imageViewButtonDisLike_celdaposteoconimagen);
            textViewCantDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdaposteoconimagen);
        }

        public void cargarPosteo(PosteoConVideo posteo) {
            Glide.with(context)
                    .load(posteo.getImagenDelUsuario())
                    .into(circleImageViewDeUsuario);
            textViewNombreDelUsuario.setText(posteo.getNombreDeUsuario());
            textViewFechaDePublicacion.setText(MiRelojDeArena.getTimeAgo(posteo.getFechaDePublicacion()));
            textViewDelPosteo.setText(posteo.getTexto());

            videoViewDelPosteo.setVideoPath(posteo.getElementoMultimedial());

            try {
                cantDislikes = posteo.getDisLike().getUsuarios().size();

            } catch (Exception e) {
                cantDislikes = 0;
            }

            try {
                cantLikes = posteo.getLike().getUsuarios().size();

            } catch (Exception e) {
                cantLikes = 0;
            }

            textViewCantLikes.setText(cantLikes.toString());
            textViewCantDisLikes.setText(cantDislikes.toString());
        }
    }

    private class PosteoSoloTextoViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageViewDeUsuario;
        private TextView textViewNombreDelUsuario;
        private TextView textViewFechaDePublicacion;
        private TextView textViewDelPosteo;
        private VideoView videoViewDelPosteo;
        private TextView textViewCantLikes;
        private ImageView imageViewButtonDisLikes;
        private TextView textViewCantDisLikes;
        private ImageView imageViewButtonResponder;

        public PosteoSoloTextoViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewDeUsuario = itemView.findViewById(R.id.circleImageViewUsuario_celdaposteoconimagen);
            textViewNombreDelUsuario = itemView.findViewById(R.id.textViewNombreDeUsuario_celdaposteoconimagen);
            textViewFechaDePublicacion = itemView.findViewById(R.id.textViewFechaDePublicacionDelPosteo_celdaposteoconimagen);
            textViewDelPosteo = itemView.findViewById(R.id.textViewTextoDelPosteo_celdaposteoconimagen);
            videoViewDelPosteo = itemView.findViewById(R.id.videoViewDelPosteo_celdaposteoconvideo);
            textViewCantLikes = itemView.findViewById(R.id.textViewCantidadDeLikesDelComentario_celdaposteoconimagen);
            imageViewButtonDisLikes = itemView.findViewById(R.id.imageViewButtonDisLike_celdaposteoconimagen);
            textViewCantDisLikes = itemView.findViewById(R.id.textViewCantidadDeDisLikes_celdaposteoconimagen);
        }

        public void cargarPosteo(PosteoConTexto posteo) {
            Glide.with(context)
                    .load(posteo.getImagenDelUsuario())
                    .into(circleImageViewDeUsuario);
            textViewNombreDelUsuario.setText(posteo.getNombreDeUsuario());
            textViewFechaDePublicacion.setText(MiRelojDeArena.getTimeAgo(posteo.getFechaDePublicacion()));
            textViewDelPosteo.setText(posteo.getTexto());


            try {
                cantDislikes = posteo.getDisLike().getUsuarios().size();

            } catch (Exception e) {
                cantDislikes = 0;
            }

            try {
                cantLikes = posteo.getLike().getUsuarios().size();

            } catch (Exception e) {
                cantLikes = 0;
            }


            textViewCantLikes.setText(cantLikes.toString());
            textViewCantDisLikes.setText(cantDislikes.toString());
        }
    }
}

