package es.aleph_tea.teabuddy.interfaces;
import es.aleph_tea.teabuddy.models.LocalizacionActividad;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalizacionActividadIn {
    @GET("/search")
    Call<LocalizacionActividad> seach(@Query("q") String query, @Query("format") String format, @Query("limit") int limit);
}
