package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.models.Actividad;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterUsuarios;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.Usuario;

public class ListaUsuariosFragment extends Fragment implements RecyclerViewInterface {

    DatabaseReference dbRef;
    RecyclerView listaUsuariosRV ;
    AdapterUsuarios adapterUsuarios;
    private ArrayList<Usuario> usuarios_aleph = new ArrayList<>();

    private Usuario usuario;
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
        dbRef = FirebaseDatabase.getInstance().getReference("UsuariosAsociacion");

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View view;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_usuarios, container, false);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listaUsuariosRV = (RecyclerView)view.findViewById(R.id.listaUsuariosRV);
        /*
        Usuario usr1 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Enrique", "Administrador");
        Usuario usr2 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Maria", "Administrador");
        Usuario usr3 = new Usuario("rdc@gmail.com", "666666666", "11/11/1111", "garcia", "Esmeralda", "Administrador");
        usuarios_aleph.add(usr1);
        usuarios_aleph.add(usr2);
        usuarios_aleph.add(usr3);


        adapterUsuarios = new AdapterUsuarios(usuarios_aleph, this);
        listaUsuariosRV.setAdapter(adapterUsuarios);
        listaUsuariosRV.setLayoutManager(new LinearLayoutManager(getContext()));
        */
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarios_aleph.clear();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    usuario = childSnapshot.getValue(Usuario.class);
                    usuarios_aleph.add(usuario);
                    Log.d("OK", "Nombre de la tarea: " + usuario.getNombre());
                }
                adapterUsuarios = new AdapterUsuarios(usuarios_aleph, ListaUsuariosFragment.this);
                listaUsuariosRV.setAdapter(adapterUsuarios);
                adapterUsuarios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
        listaUsuariosRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), modificacionUsuarios.class);
        intent.putExtra("nombre_usuario", usuarios_aleph.get(position).getNombre());
        intent.putExtra("apellido_usuario", usuarios_aleph.get(position).getApellido());
        intent.putExtra("email_usuario", usuarios_aleph.get(position).getEmail());
        intent.putExtra("n_telefono_usuario", usuarios_aleph.get(position).getN_telefono());
        intent.putExtra("fecha_nacimiento_usuario", usuarios_aleph.get(position).getFecha_nacimiento());
        intent.putExtra("rol_usuario", usuarios_aleph.get(position).getRol());
        startActivity(intent);
    }
}