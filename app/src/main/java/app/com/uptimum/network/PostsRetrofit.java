package app.com.uptimum.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import app.com.uptimum.model.Error;
import app.com.uptimum.model.Posts;

public interface PostsRetrofit {

    @GET("api/posts")
    Call<List<Posts>> getPosts(
            @Header("Authorization") String auth
    );

    @GET("api/posts/{id}")
    Call<List<Posts>> getPostsUser(
            @Header("Authorization") String auth,
            @Path("id") String iduser
    );

    @GET("api/postsdetail/{id}")
    Call<Posts> getPostsDetail(
            @Header("Authorization") String auth,
            @Path("id") String idposts
    );

    @Multipart
    @POST("api/posts")
    Call<Error> postPossts(
            @Header("Authorization") String auth,
            @Part("iduser") RequestBody iduser,
            @Part("document") RequestBody document,
            @Part List<MultipartBody.Part> upload
    );

    @POST("api/postbackground")
    @FormUrlEncoded
    Call<Error> postBackground(
            @Header("Authorization") String auth,
            @Field("iduser") String iduser,
            @Field("document") String document,
            @Field("background") String background
    );

    @Multipart
    @POST("api/comment/{id}")
    Call<Error> postFileCmt(
            @Header("Authorization") String auth,
            @Path("id") String idcmt,
            @Part MultipartBody.Part uploadfilecmt
    );

    @DELETE("api/deleteposts/{id}")
    Call<Error> deletePosts(
            @Header("Authorization") String auth,
            @Path("id") String idposts
    );

    @PUT("api/updateposts/{id}")
    @FormUrlEncoded
    Call<Error> putPosts(
            @Header("Authorization") String auth,
            @Path("id") String idposts,
            @Field("document") String document
    );

    @POST("api/posts/share")
    @FormUrlEncoded
    Call<Error> sharePosts(
            @Header("Authorization") String auth,
            @Field("iduser") String iduser,
            @Field("document") String document,
            @Field("idshareposts") String idshareposts
    );
}
