package com.vigilancia.maestria.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vigilancia.maestria.Adaptadores.DispositivosAdapter;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.Json.Dispositivos;
import com.vigilancia.maestria.R;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DispositivosFragment extends Fragment {

    public DispositivosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dispositivos_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            SecureStorageUtil storage;
            try {
                storage = new SecureStorageUtil(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = storage.getSecureData("UrlBase") + "/Dispositivos/Consultar"; // URL de la API

            // Manejar el error
            JsonArrayRequest escenarios = new JsonArrayRequest(Request.Method.GET, // Método de la solicitud
                    url, null, // No se envía ningún cuerpo en la solicitud GET
                    response -> {
                        System.out.println("Respuesta: " + response);

                        Gson gson = new Gson();

                        // Especificar el tipo de la lista
                        Type listType = new TypeToken<List<Dispositivos>>() {
                        }.getType();

                        List<Dispositivos> dispositivos = gson.fromJson(response.toString(), listType);

                        recyclerView.setAdapter(new DispositivosAdapter(dispositivos));
                    }, Throwable::printStackTrace
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
            queue.add(escenarios);
        }
        return view;
    }
}