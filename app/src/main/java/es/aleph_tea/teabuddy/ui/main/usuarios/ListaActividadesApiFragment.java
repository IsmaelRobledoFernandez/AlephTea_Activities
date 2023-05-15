package es.aleph_tea.teabuddy.ui.main.usuarios;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.actividadesAPI.APIService;
import es.aleph_tea.teabuddy.interfaces.RecyclerViewInterface;
import es.aleph_tea.teabuddy.models.ActividadAPI;
import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import es.aleph_tea.teabuddy.ui.main.adapters.AdapterActividadesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActividadesApiFragment extends Fragment implements RecyclerViewInterface {
    DatabaseReference dbRef;
    RecyclerView listaActividadesRV;
    AdapterActividadesApi adapterActividades;

    private ArrayList<ActividadAPI> listaActividades = new ArrayList<>();


    // Necesarios para la API
    private static final String API_BASE_URL = "https://datos.comunidad.madrid/catalogo/dataset/";
    private Retrofit retrofit;
    //private static final String TAG = "ACTIVIDADES";

    //protected ArrayList<ActividadAPI> listaActividades;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;


    public ListaActividadesApiFragment() {
        // Required empty public constructor
    }

    public static ListaActividadesApiFragment newInstance(String param1, String param2) {
        ListaActividadesApiFragment fragment = new ListaActividadesApiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    private View view;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_actividades_api, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and Adapter
        listaActividadesRV = view.findViewById(R.id.listaActividadesAPIRV);
        adapterActividades = new AdapterActividadesApi(ListaActividadesApiFragment.this, listaActividades);
        listaActividadesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        listaActividadesRV.setAdapter(adapterActividades);

        // Load data into listaActividades using obtenerDatos()
        obtenerDatos();
    }

    private void obtenerDatos() {
        // Initialize Retrofit and API Service
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService service = retrofit.create(APIService.class);

        Call<ActividadAPIRespuesta> actividadRespuestaCall = service.obtenerListaActividades();
        actividadRespuestaCall.enqueue(new Callback<ActividadAPIRespuesta>() {
            @Override
            public void onResponse(Call<ActividadAPIRespuesta> call, Response<ActividadAPIRespuesta> response) {
                if (response.isSuccessful()) {
                    ActividadAPIRespuesta actividadAPIRespuesta = response.body();
                    // Lista de actividades de la API
                    listaActividades = actividadAPIRespuesta.getData();
                    // Set data on adapter and notify adapter of changes
                    adapterActividades.setData(listaActividades);
                    adapterActividades.notifyDataSetChanged();
                } else {
                    Log.e("TAG", " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ActividadAPIRespuesta> call, Throwable t) {
                Log.e("TAG", " onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), MostrarInfoActividadesAPI.class);
        intent.putExtra("nombre_centro", listaActividades.get(position).getCentro_nombre());
        intent.putExtra("nombre_actividad", listaActividades.get(position).getActividad_extraexcolar_descrip());
        intent.putExtra("datos_actividad", listaActividades.get(position).getDat_nombre());
        startActivity(intent);
    }
}