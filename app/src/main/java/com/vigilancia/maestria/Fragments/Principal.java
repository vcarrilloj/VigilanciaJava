package com.vigilancia.maestria.Fragments;

import static com.vigilancia.maestria.Globales.App.IdIntentUsado;
import static com.vigilancia.maestria.Globales.App.pendingIntentMap;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vigilancia.maestria.Comun.SecureStorageUtil;
import com.vigilancia.maestria.R;


public class Principal extends Fragment {

    public Principal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();

        // Obtiene referencia al botón
        Button btnEjecutarEscenarios = view.findViewById(R.id.BtnEjecutarEscenarios);
        Button btnDsipositivos = view.findViewById(R.id.BtnDispositivos);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        // Configura el click listener del botón
        btnEjecutarEscenarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_p_to_escenarios);
            }
        });

        btnDsipositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_p_to_dispositivos);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_p_to_notificaciones);
            }
        });

        NavController navController = Navigation.findNavController(view);

        SecureStorageUtil storage;
        try {
            storage = new SecureStorageUtil(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String usuarioAutenticado = storage.getSecureData("UsuarioAut");

        if (usuarioAutenticado == null) {
            navController.navigate(R.id.action_p_to_login);
        } else {
            if (savedInstanceState == null && requireActivity().getIntent() != null) {
                boolean goNotificaciones = requireActivity().getIntent().getBooleanExtra("irNotificaciones", false);
                long idIntent = requireActivity().getIntent().getLongExtra("idIntent", 0);
                if (goNotificaciones && idIntent != IdIntentUsado) {
                    PendingIntent pi = pendingIntentMap.get(idIntent);
                    if (pi != null) {
                        pi.cancel();
                        IdIntentUsado = idIntent;
                    }
                    navController.navigate(R.id.action_p_to_notificaciones);
                }
            }
        }
    }
}