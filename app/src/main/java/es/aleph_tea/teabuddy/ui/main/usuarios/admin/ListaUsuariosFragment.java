package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.models.viewmodel.UsuarioViewModel;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterUsuarios;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;


public class ListaUsuariosFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView listaUsuariosRV ;
    AdapterUsuarios adapterUsuarios;
    private List<Usuario> usuarios_aleph = new ArrayList<>();
    private UsuarioViewModel usuarioViewModel;
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

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);

        usuarioViewModel.getAll().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                usuarios_aleph = usuarios;

                Log.d("ListaUsuariosAdmin","Tamanio: " + usuarios.size());

                adapterUsuarios = new AdapterUsuarios(usuarios_aleph, ListaUsuariosFragment.this);
                listaUsuariosRV.setAdapter(adapterUsuarios);
                adapterUsuarios.notifyDataSetChanged();
            }
        });

        listaUsuariosRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), ModificacionUsuarios.class);
        intent.putExtra("uid", usuarios_aleph.get(position).getUid());
        intent.putExtra("nombre_usuario", usuarios_aleph.get(position).getNombre());
        intent.putExtra("apellido_usuario", usuarios_aleph.get(position).getApellidos());
        intent.putExtra("email_usuario", usuarios_aleph.get(position).getEmail());
        intent.putExtra("n_telefono_usuario", usuarios_aleph.get(position).getNumero_telefono());
        intent.putExtra("fecha_nacimiento_usuario", usuarios_aleph.get(position).getFecha_nacimiento());
        intent.putExtra("rol_usuario", usuarios_aleph.get(position).getRol());
        startActivity(intent);
    }
}