package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.Usuario;

public class ListaUsuariosFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView listaUsuariosRV ;
    DatabaseReference dbRef;
    AdapterUsuarios adapterUsuarios;
    ArrayList<Usuario> usuarios_aleph = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListaUsuariosFragment() {
        // Required empty public constructor
    }


    public static ListaUsuariosFragment newInstance(String param1, String param2) {
        ListaUsuariosFragment fragment = new ListaUsuariosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_usuarios, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listaUsuariosRV = (RecyclerView)view.findViewById(R.id.listaUsuariosRV);

        Usuario usr1 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Enrique", "Administrador");
        Usuario usr2 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Maria", "Administrador");
        Usuario usr3 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Esmeralda", "Administrador");
        usuarios_aleph.add(usr1);
        usuarios_aleph.add(usr2);
        usuarios_aleph.add(usr3);


        adapterUsuarios = new AdapterUsuarios(usuarios_aleph, this);
        listaUsuariosRV.setAdapter(adapterUsuarios);
        listaUsuariosRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), modificacionUsuarios.class);
        intent.putExtra("nombre_usuario", usuarios_aleph.get(position+1).getNombre());
        intent.putExtra("apellido_usuario", usuarios_aleph.get(position+1).getApellido());
        intent.putExtra("email_usuario", usuarios_aleph.get(position+1).getEmail());
        intent.putExtra("n_telefono_usuario", usuarios_aleph.get(position+1).getN_telefono());
        intent.putExtra("fecha_nacimiento_usuario", usuarios_aleph.get(position+1).getFecha_nacimiento());
        intent.putExtra("rol_usuario", usuarios_aleph.get(position+1).getRol());
        startActivity(intent);
    }
}