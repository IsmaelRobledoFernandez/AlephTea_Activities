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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.interfaces.ListaActividades;
import es.aleph_tea.teabuddy.models.Sesion;
import es.aleph_tea.teabuddy.models.viewmodel.ActividadViewModel;
import es.aleph_tea.teabuddy.models.viewmodel.Usuario_ActividadViewModel;

public class EnrolledActivitiesFragment extends Fragment implements AdapterView.OnItemClickListener, ListaActividades {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private List<String> nombresActividades;

    private ArrayAdapter<String> mAdapter;

    AppDatabase db;

    ActividadDAO dao;

    ActividadRepository repo;

    private Map<Integer,Integer> clickAID;

    ActividadViewModel actividadViewModel;

    Usuario_ActividadViewModel usuario_actividadViewModel;

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

        List<String> nombresActividades;

        // Inicializacion de la ListView, la lista de actividades, el adapter
        // y la lista de ID's de actividad
        mListView = (ListView) view.findViewById(R.id.listViewIActivities);
        nombresActividades = new ArrayList<>();
        clickAID = new HashMap<>();

        // Viewmodel Usuario_Actividad
        usuario_actividadViewModel = ViewModelProviders.of(this).get(Usuario_ActividadViewModel.class);

        usuario_actividadViewModel.getInscritas(Sesion.SesionUid).observe(this, new Observer<List<Usuario_Actividad>>() {
            @Override
            public void onChanged(List<Usuario_Actividad> usuario_actividades) {
                Log.d("onRoomChangeUsuario_Actividad", usuario_actividades.toString());
                Log.d("onRoomChangeUsuario_Actividad", "Tamanio: " + usuario_actividades.size());

                // Obtencion y printado de todos los nombres de las actividades
                // inscritas que aparezan en la lista

                // Operamos con la base de datos para obtener las actividades correspondientes
                // A las inscripciones
                db = AppDatabase.getInstance(getContext());
                dao = db.actividadDAO();
                repo = new ActividadRepositoryImpl(dao);

                // Creamos una nueva lista de actividades inscritas
                List<Actividad> actividadesInscritas = new LinkedList<Actividad>();

                // Obtenemos la lista
                // Creamos un iterador para recorrer la lista de actividades
                List<Actividad> actividades = dao.getAll();
                Iterator<Actividad> it = actividades.iterator();

                // Creamos una variable de salida para salir del bucle
                // While cuando encontremos la actividad que contiene el id
                // de una de las inscritas por el usuario
                boolean encontrado = false;

                // Encontramos y añadimos las actividades encontradas
                // a la lista de actividades inscritas
                for (Usuario_Actividad ua : usuario_actividades) {
                    if (ua.isInscrito()) {
                        while (it.hasNext() && !encontrado) {
                            Actividad a = it.next();
                            if (a.getActividadId() == ua.getActividadId()) {
                                actividadesInscritas.add(a);
                                encontrado = true;
                            }
                        }
                        encontrado = false;
                        it = actividades.iterator(); // Reestarteamos el iterator
                    }
                }
                putActividades(nombresActividades, actividadesInscritas);
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
        if (clickAID != null)
            intent.putExtra("actividadId",clickAID.get(position));
        startActivity(intent);

    }

    private void setAdapterToView(List<String> nombresActividades) {
        // Seteo del adapter a la view
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nombresActividades);
        mListView.setAdapter(mAdapter);
    }

    private void putActividades(List<String> actividades, List<Actividad> actividadesInscritas) {
        actividades.clear();
        clickAID.clear();
        int i=0;
        for (Actividad a : actividadesInscritas) {
            Log.d("actividadID", "id: " + a.getActividadId());
            actividades.add(a.getNombre());
            clickAID.put(i,a.getActividadId());
            i++;
            Log.d("ListadoActividadesRoom", "Nombre=" + a.getNombre() +
                    ", Descripcion=" + a.getDescripcion() +
                    ", FechaHora=" + a.getFechaHora() +
                    ", Localizacion=" + a.getLocalizacion() );
        }
    }
}
