package app.com.uptimum.network;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import app.com.uptimum.model.Login;

public interface RetrofitInterface {
    @POST("api/register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("username") String username,
                                    @Field("email") String email,
                                    @Field("password") String password);

    @POST("api/login")
    @FormUrlEncoded
    Call<Login> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("api/checklogin")
    Call<Login> checkLogin(
            @Header("Authorization") String auth
    );
//    @GET("users/{email}")
//    Observable<Users> getProfile(@Path("email") String email);

//    @PUT("users/{email}")
//    Observable<Response> changePassword(@Path("email") String email, @Body Users user);
//
//    @POST("users/{email}/password")
//    Observable<Response> resetPasswordInit(@Path("email") String email);
//
//    @POST("users/{email}/password")
//    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body Users user);
}
