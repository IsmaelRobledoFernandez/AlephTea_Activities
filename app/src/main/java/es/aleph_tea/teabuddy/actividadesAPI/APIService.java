package es.aleph_tea.teabuddy.actividadesAPI;

import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("52a6591b-d851-4290-b71f-fa354e25767f/resource/6718e9f2-11b1-4381-8ad2-b71a2485e69f/download/actividades_extraescolares_institucionales.json")
    Call<ActividadAPIRespuesta> obtenerListaActividades();

}
