package es.aleph_tea.teabuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;

public class UnoFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private List<String> nombresActividades;

    private ArrayAdapter<String> mAdapter;

    private DatabaseReference mDatabase;

    private AppDatabase db;
    private ActividadDAO dao;
    private ActividadRepository repo;

    public UnoFragment() {
        // Constructor público requerido
    }

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
        nombresActividades = new ArrayList<>();

        // Inicialización FirebaseRTDB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inicialización BD ROOM
        db = AppDatabase.getInstance(this.getContext());
        dao = db.actividadDAO();
        repo = new ActividadRepositoryImpl(dao);

        // Obtención de datos en tiempo real guardados en ROOM dinámicamente
        databaseRTUpdate(mDatabase,repo);

        // Obtencion y printado de todos los nombres de las actividades en la lista
        putActividades(nombresActividades);

        // Seteo del adapter a la view
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nombresActividades);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Toast.makeText(getContext(),"Actividad seleccionada", Toast.LENGTH_SHORT).show();

        // Buscamos pasarle a la nueva actividad el id de la actividad clickada
        // Generamos el intent, y le pasamos como parametro la posicion clickada + 1
        // posicion = posicion + 1 porque los indices de la BD ROOM empiezan en 1
        // De no pasar así la posición, no se recuperaría la actividad correctamente
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailsActivity.class);
        intent.putExtra("actividadId",position + 1);
        startActivity(intent);

    }

    private void databaseRTUpdate(DatabaseReference mDatabase, ActividadRepository repo) {
        mDatabase.child("Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Snackbar.make(getView(),"Actualizando actividades...",Snackbar.LENGTH_SHORT)
                            .show();

                    Log.d("ActualizacionFB", "Hubo cambios en FBRTDB");

                    // Como hay cambios en firebase, reconstruimos la tabla de actividades
                    repo.deleteAllActividades();

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        // Obtenemos los valores en crudo de cada actividad

                        String nombre = ds.child("nombre").getValue().toString();

                        String descripcion = ds.child("descripcion").getValue().toString();

                        Long fechaHora = Long.parseLong(ds.child("fechaHora")
                                .getValue().toString());

                        String localizacion = ds.child("localizacion").getValue().toString();

                        boolean estaInscrito = Boolean.parseBoolean(
                                ds.child("estaInscrito")
                                        .getValue().toString());

                        // Creamos la actividad obtenida para guardarla en la lista
                        Actividad actividad = new Actividad();

                        // Ajustamos sus valores
                        actividad.setNombre(nombre);
                        actividad.setDescripcion(descripcion);
                        actividad.setFechaHora(fechaHora);
                        actividad.setLocalizacion(localizacion);
                        actividad.setEstaInscrito(estaInscrito);

                        // Añadimos la actividad a la base de datos ROOM
                        repo.insertActividad(actividad);

                    }

                    // Obtencion y printado de todos los nombres de las actividades en la lista
                    putActividades(nombresActividades);

                    // Seteo del adapter a la view
                    mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nombresActividades);
                    mListView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void putActividades(List<String> actividades) {
        actividades.clear();
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
}