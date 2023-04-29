package es.aleph_tea.teabuddy.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.Actividad;

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<Actividad> listaActividades;

    public AdapterActividades(RecyclerViewInterface recyclerViewInterface, ArrayList<Actividad> listaActividades) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.listaActividades = listaActividades;
    }


    @NonNull
    @Override
    public ActividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_actividad, parent, false);
        return new ActividadesViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActividades.ActividadesViewHolder holder, int position) {
        Actividad actividad = listaActividades.get(position);
        holder.nombre.setText(actividad.getNombre_actividad_str());
        holder.fecha.setText(actividad.getFecha_actividad_str());
        holder.hora.setText(actividad.getHora_actividad_str());
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public static class ActividadesViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, fecha, hora;
        public ActividadesViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_activity);
            fecha = itemView.findViewById(R.id.fecha_activity);
            hora = itemView.findViewById(R.id.hora_activity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
