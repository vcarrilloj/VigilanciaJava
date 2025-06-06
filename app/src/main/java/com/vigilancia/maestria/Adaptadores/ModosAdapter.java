package com.vigilancia.maestria.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.Json.ModoOperacion;
import com.vigilancia.maestria.databinding.FragmentModoOperacionBinding;

import java.util.List;

public class ModosAdapter extends RecyclerView.Adapter<ModosAdapter.ViewHolder> {

    private final List<ModoOperacion> mValues;
    private final Context context;

    public ModosAdapter(List<ModoOperacion> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ModosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModosAdapter.ViewHolder(FragmentModoOperacionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ModosAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtModoOperacion.setText(mValues.get(position).getNombre());
        holder.btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(context);
                SecureStorageUtil storage;
                try {
                    storage = new SecureStorageUtil(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String url = storage.getSecureData("UrlBase") + "/Condiciones/Seleccionar/" + holder.mItem.getId(); // URL de tu API

                // Manejar errores si ocurre un problema con la solicitud
                StringRequest postRequest = new StringRequest(
                        Request.Method.PUT,
                        url,
                        response -> {
                            // Si el servidor envía alguna respuesta, aunque esté vacía, este método la manejará
                            System.out.println("Petición exitosa");
                        },
                        Throwable::printStackTrace
                ) {
                    @Override
                    public java.util.Map<String, String> getHeaders() {
                        java.util.Map<String, String> headers = new java.util.HashMap<>();
                        headers.put("access_token", storage.getSecureData("access_token"));
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                // Agregar la solicitud a la cola
                queue.add(postRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtModoOperacion;
        public final Button btnSeleccionar;
        public ModoOperacion mItem;

        public ViewHolder(FragmentModoOperacionBinding binding) {
            super(binding.getRoot());
            txtModoOperacion = binding.ModoOperacion;
            btnSeleccionar = binding.Seleccionar;
        }

    }
}
