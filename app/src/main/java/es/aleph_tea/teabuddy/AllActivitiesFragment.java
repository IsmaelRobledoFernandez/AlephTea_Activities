package es.aleph_tea.teabuddy;

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
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.interfaces.ListaActividades;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.viewmodel.ActividadViewModel;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividades;

public class AllActivitiesFragment extends Fragment implements
        ListaActividades, RecyclerViewInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;

    private AdapterActividades mAdapter;

    private Map<Integer,String> clickAID;

    ActividadViewModel actividadViewModel;

    public AllActivitiesFragment() {
        // Constructor público requerido AllActivitiesFragment
    }

    public static AllActivitiesFragment newInstance(String param1, String param2) {
        AllActivitiesFragment fragment = new AllActivitiesFragment();
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
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_all_activities, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> nombresActividades;

        // Inicializacion de la ListView, la lista de actividades, el adapter
        // y la lista de ID's de actividad
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listaActividadesRV);
        nombresActividades = new ArrayList<>();
        clickAID = new HashMap<>();

        // Uso de ViewModel para escuchar cambios en la ROOM BD
        actividadViewModel = ViewModelProviders.of(this).get(ActividadViewModel.class);
        actividadViewModel.getAll().observe(this, new Observer<List<Actividad>>() {
            @Override
            public void onChanged(List<Actividad> actividades) {
                Log.d("onRoomChangeActividad", actividades.toString());
                Log.d("onRoomChangeActividad", "Tamanio: " + actividades.size());
                // Obtencion y printado de todos los nombres de las actividades en la lista
                putActividades(nombresActividades,actividades);
                setAdapterToView(actividades);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(getContext(),"Actividad seleccionada", Toast.LENGTH_SHORT).show();

        // Buscamos pasarle a la nueva actividad el id de la actividad clickada
        // Generamos el intent, y le pasamos como parametro la posicion clickada + 1
        // posicion = posicion + 1 porque los indices de la BD ROOM empiezan en 1
        // De no pasar así la posición, no se recuperaría la actividad correctamente
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailsActivity.class);
        if (clickAID != null)
            intent.putExtra("actividadId",clickAID.get(position));
        startActivity(intent);

    }

    private void setAdapterToView(List<Actividad> actividades) {
        // Seteo del adapter a la view
        mAdapter = new AdapterActividades(AllActivitiesFragment.this,actividades);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void putActividades(List<String> actividades,List<Actividad> actividadesTotales) {
        actividades.clear();
        clickAID.clear();
        int i=0;
        for (Actividad a: actividadesTotales) {
            actividades.add(a.getNombre());
            clickAID.put(i,a.getActividadId());
            i++;
            Log.d("ListadoActividadesRoom", "Nombre=" + a.getNombre() +
                    ", Descripcion=" + a.getDescripcion() +
                    ", FechaHora=" + a.getFechaHora() +
                    ", Localizacion=" + a.getLocalizacion());
        }
    }
}