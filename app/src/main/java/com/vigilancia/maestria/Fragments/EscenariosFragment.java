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
import com.vigilancia.maestria.Adaptadores.EscenariosAdapter;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.Json.Escenarios;
import com.vigilancia.maestria.R;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EscenariosFragment extends Fragment {

    public EscenariosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_escenarios_list, container, false);

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
            String url = storage.getSecureData("UrlBase") + "/Escenarios/Consultar/"; // URL de la API

            // Manejar el error
            JsonArrayRequest escenarios = new JsonArrayRequest(Request.Method.GET, // Método de la solicitud
                    url, null, // No se envía ningún cuerpo en la solicitud GET
                    response -> {
                        System.out.println("Respuesta: " + response);

                        Gson gson = new Gson();

                        // Especificar el tipo de la lista
                        Type listType = new TypeToken<List<Escenarios>>() {
                        }.getType();

                        List<Escenarios> escenarios1 = gson.fromJson(response.toString(), listType);

                        recyclerView.setAdapter(new EscenariosAdapter(EliminarDuplicados(escenarios1), context));
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

    private List<Escenarios> EliminarDuplicados(List<Escenarios> lista) {
        Set<Integer> idsUnicos = new HashSet<>();  // Almacenar IDs únicos

        // Iterador para recorrer la lista
        // Si el ID ya existe en el set, eliminar el objeto
        // Eliminar elemento con ID duplicado
        lista.removeIf(escenario -> !idsUnicos.add(escenario.getId()));

        return lista;
    }
}