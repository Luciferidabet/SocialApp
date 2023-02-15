package app.com.uptimum.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import app.com.uptimum.model.Error;
import app.com.uptimum.model.Story;

public interface StoryRetrofit {
    @GET("api/story")
    Call<List<Story>> getStory(
            @Header("Authorization") String auth
    );

    @Multipart
    @POST("api/story/{id}")
    Call<Error> postStory(
            @Header("Authorization") String auth,
            @Path("id") String id,
            @Part List<MultipartBody.Part> upload
    );
}
