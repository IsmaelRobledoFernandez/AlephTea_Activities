package es.aleph_tea.teabuddy;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

//import es.aleph_tea.teabuddy.controllers.FBRTDatabaseController;
import java.util.ArrayList;

import es.aleph_tea.teabuddy.actividadesAPI.ActividadesAPIService;
import es.aleph_tea.teabuddy.databinding.ActivityActivitiesListBinding;
import es.aleph_tea.teabuddy.login.AccountDetailsActivity;
import es.aleph_tea.teabuddy.models.ActividadAPI;
import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import es.aleph_tea.teabuddy.ui.main.SectionsPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIActivitiesListActivity extends AppCompatActivity {

    private Retrofit retrofit;
    // private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/209548-2083609-censo-locales-historico";

    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private static final String ERROR = "ERROR API";

    ImageView ajustes;
    ImageView cuentaUsuario;
    private ActivityActivitiesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivitiesListBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_api_activities_list);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // Inicializacion botón de ajustes
        accesoAjustes();

        // Inicializacion del boton que hace referencia a la cuenta del usuario
        accesoCuentaUsuario();

        // Inicializacion metodos para obtención de datos con Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    protected void accesoAjustes(){
        ajustes = (ImageView) findViewById(R.id.settingsButton);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

    }

    protected void accesoCuentaUsuario() {
        cuentaUsuario = (ImageView) findViewById(R.id.accountButton);
        cuentaUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountDetailsActivity.class);
                startActivity(i);
            }
        });
    }

    private void obtenerDatos(){
        ActividadesAPIService service = retrofit.create(ActividadesAPIService.class);
        Call<ActividadAPIRespuesta> APIActividadRespuestaCall = service.obtenerListaActividades();

        APIActividadRespuestaCall.enqueue(new Callback<ActividadAPIRespuesta>() {
            @Override
            public void onResponse(Call<ActividadAPIRespuesta> call, Response<ActividadAPIRespuesta> response) {
                if(response.isSuccessful()){
                    ActividadAPIRespuesta actividadAPI = response.body();
                    ArrayList<ActividadAPI> listaActividades = actividadAPI.getResults();
                    for (int i = 0; i < listaActividades.size(); i++) {
                        ActividadAPI a = listaActividades.get(i);
                        Log.i(TAG, "Actividad: " + a.getClase_vial_edificio());
                    }
                }else{
                    Log.e(TAG, "On Response: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<ActividadAPIRespuesta> call, Throwable t) {
                Log.e(TAG, "On Response: " + t.getMessage());

            }
        });


    }


}