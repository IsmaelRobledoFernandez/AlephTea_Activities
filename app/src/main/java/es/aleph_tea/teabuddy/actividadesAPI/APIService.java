package es.aleph_tea.teabuddy.actividadesAPI;

import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("share/events-share-new/29427")
    Call<ActividadAPIRespuesta> obtenerListaActividades();

}
