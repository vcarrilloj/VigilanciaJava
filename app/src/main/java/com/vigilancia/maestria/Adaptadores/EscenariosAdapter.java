package com.vigilancia.maestria.Adaptadores;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.Json.Escenarios;
import com.vigilancia.maestria.databinding.FragmentEscenariosBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Escenarios}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EscenariosAdapter extends RecyclerView.Adapter<EscenariosAdapter.ViewHolder> {

    private final List<Escenarios> mValues;
    private final Context context;

    public EscenariosAdapter(List<Escenarios> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentEscenariosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getIdString());
        holder.mContentView.setText(mValues.get(position).getNombre());
        holder.btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(context);
                SecureStorageUtil storage;
                try {
                    storage = new SecureStorageUtil(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String url = storage.getSecureData("UrlBase") + "/Escenarios/Ejecutar/" + holder.mItem.getIdString(); // URL de tu API

                // Manejar errores si ocurre un problema con la solicitud
                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        url,
                        response -> {
                            // Si el servidor envía alguna respuesta, aunque esté vacía, este método la manejará
                            System.out.println("Petición exitosa");
                        },
                        Throwable::printStackTrace
                ){
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
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button btnEjecutar;
        public Escenarios mItem;

        public ViewHolder(FragmentEscenariosBinding binding) {
            super(binding.getRoot());
            mIdView = binding.IdEscenario;
            mContentView = binding.NombreEscenario;
            btnEjecutar = binding.Ejecutar;
        }

    }
}