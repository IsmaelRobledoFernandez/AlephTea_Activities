package es.aleph_tea.teabuddy.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.ActividadAPI;
import es.aleph_tea.teabuddy.models.ActividadAPI;

public class AdapterActividadesApi extends RecyclerView.Adapter<AdapterActividadesApi.ActividadesViewHolder> {
    //private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<ActividadAPI> listaActividades;

    private Context context;

   /* public AdapterActividadesApi(RecyclerViewInterface recyclerViewInterface, ArrayList<ActividadAPI> listaActividades) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.listaActividades = listaActividades;
    }*/

    public AdapterActividadesApi(){
        listaActividades = new ArrayList<>();
    }


    @NonNull
    @Override
    public ActividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_actividad, parent, false);
        return new ActividadesViewHolder(view); // , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActividadesApi.ActividadesViewHolder holder, int position) {
        ActividadAPI actividad = listaActividades.get(position);
        holder.nombre_activity.setText(actividad.getActividad_extraexcolar_descrip());
        holder.centro_nombre.setText(actividad.getCentro_nombre());
        holder.dat_nombre.setText(actividad.getDat_nombre());
    }

    @Override
    public int getItemCount() {

        return listaActividades.size();
    }

    public void adiccionarListaActividades(ArrayList<ActividadAPI> listaActividades) {

        listaActividades.addAll(listaActividades);
        notifyDataSetChanged();
    }

    public static class ActividadesViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_activity, centro_nombre, dat_nombre;
        public ActividadesViewHolder(View itemView) /*, RecyclerViewInterface recyclerViewInterface) */{
            super(itemView);
            nombre_activity = (TextView) itemView.findViewById(R.id.nombre_activity);
            centro_nombre = (TextView)itemView.findViewById(R.id.centro_nombre);
            dat_nombre = (TextView)itemView.findViewById(R.id.dat_nombre);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });*/
        }
    }
}
