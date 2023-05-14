package es.aleph_tea.teabuddy.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    List<Actividad> listaActividades;

    public AdapterActividades(RecyclerViewInterface recyclerViewInterface, List<Actividad> listaActividades) {
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
        holder.nombre.setText(actividad.getNombre());
        //Generamos un formato para la fecha
        DateFormat fecha = new SimpleDateFormat("dd/mm/yyyy");
        holder.fecha.setText(fecha.format(new Date(new Timestamp(actividad.getFechaHora()).getTime())));
        //Generamos un formato para la hora
        DateFormat hora = new SimpleDateFormat("hh:mm");
        holder.hora.setText(hora.format(new Date(new Timestamp(actividad.getFechaHora()).getTime())));
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
