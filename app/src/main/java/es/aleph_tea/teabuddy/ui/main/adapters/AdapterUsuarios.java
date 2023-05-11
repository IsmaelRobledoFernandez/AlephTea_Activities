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
import es.aleph_tea.teabuddy.models.Usuario;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuariosViewHolder> {
    ArrayList<Usuario> listaUsuarios;
    private final RecyclerViewInterface recyclerViewInterface;

    public AdapterUsuarios(ArrayList<Usuario> listaUsuarios, RecyclerViewInterface recyclerViewInterface){
        this.listaUsuarios = listaUsuarios;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public AdapterUsuarios.UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_usuario, parent, false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsuarios.UsuariosViewHolder holder, int position) {
        holder.getNombreTV().setText(listaUsuarios.get(position).getNombre()+" "+listaUsuarios.get(position).getApellido());
        holder.getCorreoTV().setText(listaUsuarios.get(position).getEmail());
        holder.getRolTV().setText(listaUsuarios.get(position).getRol());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, correo, rol;

        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_usuario);
            correo = itemView.findViewById(R.id.correo_usuario);
            rol = itemView.findViewById(R.id.rol_asociacion);

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
        public TextView getNombreTV(){
            return nombre;
        }
        public TextView getCorreoTV(){
            return correo;
        }
        public TextView getRolTV(){
            return rol;
        }
    }
}
