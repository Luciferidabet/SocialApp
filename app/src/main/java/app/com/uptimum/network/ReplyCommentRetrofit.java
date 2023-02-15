package app.com.uptimum.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import app.com.uptimum.model.ReplyComment;

public interface ReplyCommentRetrofit {
    @GET("api/replycmt/{id}")
    Call<ReplyComment> getReplyCmt(
            @Header("Authorization") String auth,
            @Path("id") String idcmt
    );
}
