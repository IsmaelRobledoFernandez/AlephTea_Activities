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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.ActivitiesListActivity;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.Actividad;
import es.aleph_tea.teabuddy.ui.main.usuarios.monitor.MainActivityMonitor;

public class AdminListaActividadesFragment extends Fragment implements RecyclerViewInterface {

    private String data;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    RecyclerView listaActividadesRV;
    ArrayList<Actividad> lista_actividades = new ArrayList<>();

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
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_lista_actividades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaActividadesRV = (RecyclerView) view.findViewById(R.id.listaActividadesRV);
        dbRef.child("Actividades").addListenerForSingleValueEvent(new ValueEventListener() { //addValueEventListener
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    data = snapshot.toString();
                    Log.d("OK", data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Actividad actividad1 = new Actividad("Paseo por el prado Valverde", "Paseo por el prado con un grupo de mayores de dieciseis y con un equipo de dos monitores", "11/11/11", "12:00-14:00", "hiperenlace",4, 2);
        Actividad actividad2 = new Actividad("Paseo por el parque", "Paseo por el parque con un grupo de mayores de dieciseis y con un equipo de dos monitores", "11/11/11", "12:00-14:00", "hiperenlace",4, 2);
        Actividad actividad3 = new Actividad("Visita al caixaforum", "Visita al caixaforum con un grupo de mayores de dieciseis y con un equipo de dos monitores", "11/11/11", "12:00-14:00", "hiperenlace",4, 2);
        Actividad actividad4 = new Actividad("Festival de pintura rupestre", "Festival de pintura rupestre con un grupo de mayores de dieciseis y con un equipo de dos monitores", "11/11/11", "12:00-14:00", "hiperenlace",4, 2);
        Actividad actividad5 = new Actividad("Visita a la granjaescuela", "Visita a la granjaescuela con un grupo de mayores de dieciseis y con un equipo de dos monitores", "11/11/11", "12:00-14:00", "hiperenlace",4, 2);

        lista_actividades.add(actividad1);
        lista_actividades.add(actividad2);
        lista_actividades.add(actividad3);
        lista_actividades.add(actividad4);
        lista_actividades.add(actividad5);

        adapterActividades = new AdapterActividades(this, lista_actividades);
        listaActividadesRV.setAdapter(adapterActividades);
        listaActividadesRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), modificacionActividades.class);
        intent.putExtra("nombre_actividad", lista_actividades.get(position).getNombre());
        intent.putExtra("fecha_actividad", lista_actividades.get(position).getFecha());
        intent.putExtra("hora_actividad", lista_actividades.get(position).getHora());
        intent.putExtra("descripcion_actividad", lista_actividades.get(position).getDescripcion());
        intent.putExtra("localizacion_actividad", lista_actividades.get(position).getLocalizacion());
        startActivity(intent);
    }
}