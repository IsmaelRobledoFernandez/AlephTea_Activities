package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

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

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;

public class MonitorListaActividadesFragment extends Fragment implements RecyclerViewInterface {

    DatabaseReference dbRef;
    RecyclerView listaActividadesRV;

    private ArrayList<Actividad> lista_actividades = new ArrayList<>();

    private Actividad actividad;
    AdapterActividades adapterActividades;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MonitorListaActividadesFragment() {
        // Required empty public constructor
    }

    public static MonitorListaActividadesFragment newInstance(String param1, String param2) {
        MonitorListaActividadesFragment fragment = new MonitorListaActividadesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monitor_lista_actividades, container, false);
        return view;
    }

    private View view;

    private FloatingActionButton floatingActionButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaActividadesRV = (RecyclerView) view.findViewById(R.id.listaActividadesMonitorRV);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista_actividades.clear();
                actividad = new Actividad();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    actividad.setActividadId(childSnapshot.getKey());
                    actividad.setNombre(childSnapshot.child("nombre").toString());
                    actividad.setDescripcion(childSnapshot.child("descripcion").toString());
                    actividad.setLocalizacion(childSnapshot.child("localizacion").toString());
                    actividad.setFechaHora(Long.parseLong(
                            childSnapshot.child("fechaHora").getValue().toString()));
                    actividad.setNumero_voluntarios(Integer.parseInt(
                            childSnapshot.child("numero_voluntarios").getValue().toString()));
                    actividad.setNumero_monitores(Integer.parseInt(
                            childSnapshot.child("numero_monitores").getValue().toString()));
                    actividad.setNumero_voluntarios_max(Integer.parseInt(
                            childSnapshot.child("numero_voluntarios_max").getValue().toString()));
                    actividad.setNumero_monitores_max(Integer.parseInt(
                            childSnapshot.child("numero_monitores_max").getValue().toString()));
                    lista_actividades.add(actividad);
                    Log.d("OK", "Nombre de la tarea: " + actividad.getNombre());
                }
                adapterActividades = new AdapterActividades(MonitorListaActividadesFragment.this, lista_actividades);
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
        Intent intent = new Intent(getContext(), MostrarInfoActividades.class);
        intent.putExtra("nombre_actividad", lista_actividades.get(position).getNombre());
        intent.putExtra("fecha_actividad", lista_actividades.get(position).getFechaHora());
        intent.putExtra("hora_actividad", lista_actividades.get(position).getFechaHora());
        intent.putExtra("descripcion_actividad", lista_actividades.get(position).getDescripcion());
        intent.putExtra("localizacion_actividad", lista_actividades.get(position).getLocalizacion());
        startActivity(intent);
    }
}