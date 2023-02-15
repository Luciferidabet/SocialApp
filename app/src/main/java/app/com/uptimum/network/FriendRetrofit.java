package app.com.uptimum.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import app.com.uptimum.model.Friend;

public interface FriendRetrofit {
    @GET("api/friend/{id}")
    Call<List<Friend>> getFriend(
            @Header("Authorization") String auth,
            @Path("id") String iduser
    );
}
