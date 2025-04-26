package com.vigilancia.maestria.Adaptadores;

import static com.vigilancia.maestria.Comun.Utilidades.base64ToBitmap;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vigilancia.maestria.Bd.Notificacion;
import com.vigilancia.maestria.R;
import com.vigilancia.maestria.databinding.FragmentNotificacionesBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Notificacion}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {

    private final List<Notificacion> notificaciones;
    private final Context context;

    public NotificacionesAdapter(List<Notificacion> items, Context context) {
        notificaciones = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentNotificacionesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.notificacion = notificaciones.get(position);
        holder.titulo.setText(notificaciones.get(position).getTitulo());
        holder.fecha.setText(notificaciones.get(position).getFecha().toString());
        holder.mensaje.setText(notificaciones.get(position).getMensaje());
        Bitmap image = base64ToBitmap(notificaciones.get(position).getImagen());
        holder.ImgNotificacion.setImageBitmap(image);

        holder.ImgNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);

                // Inflar el layout personalizado
                dialog.setContentView(R.layout.popup_image);

                // Obtener la ImageView del layout
                ImageView imageView = dialog.findViewById(R.id.popup_image_view);

                // Si deseas cambiar la imagen dinámicamente
                imageView.setImageBitmap(image);

                // Mostrar el diálogo
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titulo;
        public final TextView fecha;
        public final TextView mensaje;
        public final ImageButton ImgNotificacion;
        public Notificacion notificacion;

        public ViewHolder(FragmentNotificacionesBinding binding) {
            super(binding.getRoot());
            titulo = binding.Titulo;
            fecha = binding.Fecha;
            mensaje = binding.Mensaje;
            ImgNotificacion = binding.ImagenNot;
        }

    }
}