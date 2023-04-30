package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import es.aleph_tea.teabuddy.ActivitiesListActivity;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.Actividad;
import es.aleph_tea.teabuddy.ui.main.usuarios.monitor.MainActivityMonitor;

public class AdminListaActividadesFragment extends Fragment implements RecyclerViewInterface {
    DatabaseReference dbRef;
    RecyclerView listaActividadesRV;
    private ArrayList<Actividad> lista_actividades = new ArrayList<>();
    private Actividad actividad;
    AdapterActividades adapterActividades;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdminListaActividadesFragment() {
        // Required empty public constructor
    }

    public static AdminListaActividadesFragment newInstance(String param1, String param2) {
        AdminListaActividadesFragment fragment = new AdminListaActividadesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dbRef = FirebaseDatabase.getInstance().getReference("ActividadesAsociacion");

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
        view = inflater.inflate(R.layout.fragment_admin_lista_actividades, container, false);
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaActividadesRV = (RecyclerView) view.findViewById(R.id.listaActividadesRV);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista_actividades.clear();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    actividad = childSnapshot.getValue(Actividad.class);
                    lista_actividades.add(actividad);
                    Log.d("OK", "Nombre de la tarea: " + actividad.getNombre_actividad_str());
                }
                adapterActividades = new AdapterActividades(AdminListaActividadesFragment.this, lista_actividades);
                listaActividadesRV.setAdapter(adapterActividades);
                adapterActividades.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
        listaActividadesRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), modificacionActividades.class);
        intent.putExtra("nombre_actividad", lista_actividades.get(position).getNombre_actividad_str());
        intent.putExtra("fecha_actividad", lista_actividades.get(position).getFecha_actividad_str());
        intent.putExtra("hora_actividad", lista_actividades.get(position).getHora_actividad_str());
        intent.putExtra("descripcion_actividad", lista_actividades.get(position).getDescripcion_actividad_str());
        intent.putExtra("localizacion_actividad", lista_actividades.get(position).getLocalizacion_str());
        startActivity(intent);
    }

}