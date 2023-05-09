package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import es.aleph_tea.teabuddy.actividadesAPI.APIService;
import es.aleph_tea.teabuddy.models.ActividadAPI;
import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIActivities {
    private static final String API_BASE_URL = "https://datos.comunidad.madrid/catalogo/dataset/";
    private Retrofit retrofit;
    private static final String TAG = "ACTIVIDADES";

    protected ArrayList<ActividadAPI> listaActividades;

    public ArrayList<ActividadAPI> getListaActividades() {
        return listaActividades;
    }

    public APIActivities(){}
    public void obtenerDatos() {
        APIService service = retrofit.create(APIService.class);
        Call<ActividadAPIRespuesta> actividadRespuestaCall = service.obtenerListaActividades();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //obtenerDatos();
        actividadRespuestaCall.enqueue(new Callback<ActividadAPIRespuesta>() {
            @Override
            public void onResponse(Call<ActividadAPIRespuesta> call, Response<ActividadAPIRespuesta> response) {

                if (response.isSuccessful()) {
                    ActividadAPIRespuesta actividadAPIRespuesta = response.body();
                    // Lista de actividades de la API
                    listaActividades = actividadAPIRespuesta.getData();
                    Log.d("Lista_actividades", listaActividades.toString());

                    //for (int i = 0; i < 10 /*listaActividades.size()*/; i++) {
                      //  Log.d(TAG, "actividad: " + listaActividades.get(i).getActividad_extraexcolar_descrip());
                    //}

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ActividadAPIRespuesta> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}