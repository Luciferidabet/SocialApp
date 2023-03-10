package app.com.uptimum.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import app.com.uptimum.model.Error;
import app.com.uptimum.model.ProfileUser;

public interface ProfileRetrofit {
    @GET("api/profile/{id}")
    Call<ProfileUser> getProfile(
            @Header("Authorization") String auth,
            @Path("id") String iduser
    );

    @POST("api/profile/{id}")
    @FormUrlEncoded
    Call<Error> postProfile(
            @Header("Authorization") String auth,
            @Path("id") String iduser,
            @Field("nickname") String nickname,
            @Field("phone") String phone,
            @Field("dateofbirth") String dateofbirth,
            @Field("studies_at") String studies_at,
            @Field("studied_at") String studied_at,
            @Field("placeslive") String placeslive,
            @Field("from") String from,
            @Field("job") String job
    );

    @POST("api/profileusername/{id}")
    @FormUrlEncoded
    Call<Error> postUserName(
            @Header("Authorization") String auth,
            @Path("id") String iduser,
            @Field("username") String username
    );

    @Multipart
    @POST("api/{type}/{id}")
    Call<Error> postImgProfile(
            @Header("Authorization") String auth,
            @Path("id") String iduser,
            @Path("type") String type,
            @Part MultipartBody.Part upload
    );
}
