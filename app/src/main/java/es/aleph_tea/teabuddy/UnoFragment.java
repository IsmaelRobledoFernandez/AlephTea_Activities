package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnoFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private List<String> actividades;

    private ArrayAdapter<String> mAdapter;

    private AppDatabase db;
    private ActividadDAO dao;
    private ActividadRepository repo;

    public UnoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnoFragment newInstance(String param1, String param2) {
        UnoFragment fragment = new UnoFragment();
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
        return inflater.inflate(R.layout.fragment_uno, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializacion de la ListView, la lista de actividades y el adapter
        mListView = (ListView)view.findViewById(R.id.listView);
        actividades = new ArrayList<>();

        // Inicialización BD Room
        db = AppDatabase.getInstance(this.getContext());
        dao = db.actividadDAO();
        repo = new ActividadRepositoryImpl(dao);

        // Prueba de inserción
        Actividad actividad = new Actividad();
        actividad.setNombre("Visita guidada al centro de madrid");
        actividad.setDescripcion("Visita guapa por el centro de madrid");
        actividad.setFechaHora(738738748L);
        actividad.setLocalizacion("Madrid Centro");
        actividad.setEstaInscrito(false);
        repo.insertActividad(actividad);

        // Prueba de inserción 2
        Actividad actividad2 = new Actividad();
        actividad2.setNombre("Visita guidada al museo del prado");
        actividad2.setDescripcion("Visita chula por el paseo del prado");
        actividad2.setFechaHora(3784738L);
        actividad2.setLocalizacion("Atocha");
        actividad2.setEstaInscrito(true);
        repo.insertActividad(actividad2);

        // Obtencion y printado de todas las actividades
        putActividades(actividades);

        // Seteo del adapter a la view
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,actividades);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }

    private void putActividades(List<String> actividades) {
        List<Actividad> actividadesTotales = repo.getAllActividades();
        for (Actividad i: actividadesTotales) {
            actividades.add(i.getNombre());
            Log.d("ListadoActividadesRoom", "Nombre=" + i.getNombre() +
                    ", Descripcion=" + i.getDescripcion() +
                    ", FechaHora=" + i.getFechaHora() +
                    ", Localizacion=" + i.getLocalizacion() +
                    ", EstaInscrito=" + i.getEstaInscrito());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Toast.makeText(getContext(),"Elemento clickado: " + position, Toast.LENGTH_SHORT).show();

        // Buscamos pasarle a la nueva actividad el id de la actividad clickada
        // Generamos el intent, y le pasamos como parametro la posicion clickada + 1
        // posicion = posicion + 1 porque los indices de la BD empiezan en 1
        // De no pasar así la posición, no se recuperaría la actividad correctamente
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailsActivity.class);
        intent.putExtra("actividadId",position + 1);
        startActivity(intent);

    }
}