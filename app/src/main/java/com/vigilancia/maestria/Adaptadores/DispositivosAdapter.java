package com.vigilancia.maestria.Adaptadores;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigilancia.maestria.Json.Dispositivos;
import com.vigilancia.maestria.databinding.FragmentDispositivosBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Dispositivos}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DispositivosAdapter extends RecyclerView.Adapter<DispositivosAdapter.ViewHolder> {

    private final List<Dispositivos> mValues;

    public DispositivosAdapter(List<Dispositivos> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentDispositivosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txvNombre.setText(mValues.get(position).getCustomName());
        holder.txvIp.setText(mValues.get(position).getIp());
        holder.txvEstado.setText(mValues.get(position).EstadoActual());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txvNombre;
        public final TextView txvIp;
        public final TextView txvEstado;
        public Dispositivos mItem;

        public ViewHolder(FragmentDispositivosBinding binding) {
            super(binding.getRoot());
            txvNombre = binding.NombreDispositivo;
            txvIp = binding.IpDispositivo;
            txvEstado = binding.EstadoDispositivo;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txvIp.getText() + "'";
        }
    }
}