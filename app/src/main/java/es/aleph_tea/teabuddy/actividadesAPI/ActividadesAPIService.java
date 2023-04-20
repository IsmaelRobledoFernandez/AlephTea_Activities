package es.aleph_tea.teabuddy.actividadesAPI;

// import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.models.ActividadAPIRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ActividadesAPIService {
    @GET("209548-2083609-censo-locales-historico.1")
    Call<ActividadAPIRespuesta> obtenerListaActividades();


}
