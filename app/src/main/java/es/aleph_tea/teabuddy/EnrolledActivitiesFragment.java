package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.interfaces.ListaActividades;
import es.aleph_tea.teabuddy.models.viewmodel.ActividadViewModel;

public class EnrolledActivitiesFragment extends Fragment implements AdapterView.OnItemClickListener, ListaActividades {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private List<Integer> posicionActividadId;

    private List<String> nombresActividades;

    private ArrayAdapter<String> mAdapter;

    ActividadViewModel actividadViewModel;

    public EnrolledActivitiesFragment() {
        // Constructor público requerido
    }

    public static EnrolledActivitiesFragment newInstance(String param1, String param2) {
        EnrolledActivitiesFragment fragment = new EnrolledActivitiesFragment();
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
        return inflater.inflate(R.layout.fragment_enrolled_activities, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializacion de la ListView, la lista de actividades, el adapter,
        // y finalmente la lista de mapeo de posiciones de elementos
        // de la lista con sus actividades inscritas correspondientes
        mListView = (ListView) view.findViewById(R.id.listViewIActivities);
        posicionActividadId = new ArrayList<>();
        nombresActividades = new ArrayList<>();

        // Viewmodel
        actividadViewModel = ViewModelProviders.of(this).get(ActividadViewModel.class);
        actividadViewModel.getInscritas().observe(this, new Observer<List<Actividad>>() {
            @Override
            public void onChanged(List<Actividad> actividades) {
                Log.d("onRoomChangeInscritas", actividades.toString());
                Log.d("onRoomChangeInscritas", "Tamanio: " + actividades.size());
                // Obtencion y printado de todos los nombres de las actividades inscritas en la lista
                putActividades(nombresActividades, actividades);
                setAdapterToView(nombresActividades);
            }
        });

        setAdapterToView(nombresActividades);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Toast.makeText(getContext(), "Actividad inscrita seleccionada", Toast.LENGTH_SHORT).show();

        // Buscamos pasarle a la nueva actividad el id de la actividad clickada.
        // Generamos el intent, y le pasamos como parametro el actividadId
        // mapeado a la posicion que ocupa la actividad en la lista
        // de actividades inscritas
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailsActivity.class);
        intent.putExtra("actividadId", posicionActividadId.get(position));
        startActivity(intent);

    }

    private void setAdapterToView(List<String> nombresActividades) {
        // Seteo del adapter a la view
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nombresActividades);
        mListView.setAdapter(mAdapter);
    }

    private void putActividades(List<String> actividades, List<Actividad> actividadesInscritas) {
        actividades.clear();
        for (Actividad i : actividadesInscritas) {
            Log.d("actividadID", "id: " + i.getActividadId());
            posicionActividadId.add(i.getActividadId());
            actividades.add(i.getNombre());
            Log.d("ListadoActividadesRoom", "Nombre=" + i.getNombre() +
                    ", Descripcion=" + i.getDescripcion() +
                    ", FechaHora=" + i.getFechaHora() +
                    ", Localizacion=" + i.getLocalizacion() +
                    ", EstaInscrito=" + i.getEstaInscrito());
        }
    }
}