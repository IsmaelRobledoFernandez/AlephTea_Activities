package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;
import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;

public class MonitorListaActividadesInscritoFragment extends Fragment implements RecyclerViewInterface{

    RecyclerView listaActividadesRV;

    List<Actividad> lista_actividades = new ArrayList<>();

    AdapterActividades adapterActividades;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MonitorListaActividadesInscritoFragment() {
        // Required empty public constructor
    }

    public static MonitorListaActividadesInscritoFragment newInstance(String param1, String param2) {
        MonitorListaActividadesInscritoFragment fragment = new MonitorListaActividadesInscritoFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor_lista_actividades_inscrito, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lista_actividades.clear();
        listaActividadesRV = (RecyclerView) view.findViewById(R.id.listaActividadesMonitorApuntadoRV);

        // TODO: Obtener actividades a las que esta inscrito el monitor

        Actividad actividad1 = new Actividad();
        Actividad actividad3 = new Actividad();
        Actividad actividad5 = new Actividad();

        actividad1.setNombre("Paseo por el prado");
        actividad1.setDescripcion("Paseo por el prado con un grupo de mayores de dieciseis y con un equipo de dos monitores");
        actividad1.setFechaHora(3478465l);
        actividad1.setLocalizacion("fioror");

        actividad3.setNombre("Paseo por el prado");
        actividad3.setDescripcion("Paseo por el prado con un grupo de mayores de dieciseis y con un equipo de dos monitores");
        actividad3.setFechaHora(3478465l);
        actividad3.setLocalizacion("fioror");

        actividad5.setNombre("Paseo por el prado");
        actividad5.setDescripcion("Paseo por el prado con un grupo de mayores de dieciseis y con un equipo de dos monitores");
        actividad5.setFechaHora(3478465l);
        actividad5.setLocalizacion("fioror");

        lista_actividades.add(actividad1);
        lista_actividades.add(actividad3);
        lista_actividades.add(actividad5);

        adapterActividades = new AdapterActividades(this, lista_actividades);
        listaActividadesRV.setAdapter(adapterActividades);
        listaActividadesRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), MostrarInfoMisActividades.class);
        intent.putExtra("nombre_actividad", lista_actividades.get(position).getNombre());
        intent.putExtra("fecha_actividad", lista_actividades.get(position).getFechaHora());
        intent.putExtra("hora_actividad", lista_actividades.get(position).getFechaHora());
        intent.putExtra("descripcion_actividad", lista_actividades.get(position).getDescripcion());
        intent.putExtra("localizacion_actividad", lista_actividades.get(position).getLocalizacion());
        startActivity(intent);
    }

}