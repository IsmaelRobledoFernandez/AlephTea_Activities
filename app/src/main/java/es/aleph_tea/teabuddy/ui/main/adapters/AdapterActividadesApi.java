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
import es.aleph_tea.teabuddy.models.ActividadAPI;

public class AdapterActividadesApi extends RecyclerView.Adapter<AdapterActividadesApi.ActividadesViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<ActividadAPI> listaActividades;

    public AdapterActividadesApi(RecyclerViewInterface recyclerViewInterface, ArrayList<ActividadAPI> listaActividades) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.listaActividades = listaActividades;
    }

    public void setData(ArrayList<ActividadAPI> listaActividades) {
        this.listaActividades = listaActividades;
    }

    @NonNull
    @Override
    public ActividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_actividad_api, parent, false);
        return new ActividadesViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesViewHolder holder, int position) {
        ActividadAPI actividad = listaActividades.get(position);
        holder.nombre_actividad_api.setText(actividad.getname());
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public static class ActividadesViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_actividad_api;
        public ActividadesViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nombre_actividad_api = itemView.findViewById(R.id.nombre_actividad_api);

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
