package com.vigilancia.maestria.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vigilancia.maestria.Adaptadores.NotificacionesAdapter;
import com.vigilancia.maestria.Bd.Notificacion;
import com.vigilancia.maestria.Bd.NotificacionesDatabase;
import com.vigilancia.maestria.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A fragment representing a list of Items.
 */
public class NotificacionesFragment extends Fragment {

    Executor executor = Executors.newSingleThreadExecutor();

    public NotificacionesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notificaciones_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            NotificacionesDatabase db = NotificacionesDatabase.getDatabase(context);


            executor.execute(new Runnable() {
                @Override
                public void run() {

                    List<Notificacion> notificaciones = db.notificacionDao().obtenerTodasLasNotificaciones();

                    // Actualizar la UI en el hilo principal (UI Thread)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new NotificacionesAdapter(notificaciones, context));
                        }
                    });
                }
            });
        }
        return view;
    }
}