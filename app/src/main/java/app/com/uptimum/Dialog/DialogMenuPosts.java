package app.com.uptimum.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import app.com.uptimum.R;
import app.com.uptimum.model.Error;
import app.com.uptimum.network.NetworkUtil;
import app.com.uptimum.network.PostsRetrofit;

public class DialogMenuPosts extends BottomSheetDialogFragment implements View.OnClickListener{
    private static final String TAG = "DialogMenuPosts";
    private LinearLayout linearMenuPosts1, linearMenuPosts2, linearMenuPosts3;
    private ImageView imgMenuPosts1, imgMenuPosts2, imgMenuPosts3;
    private TextView txtMenuPosts1, txtMenuPosts2, txtMenuPosts3;
    private View view;
    private Context context;
    private String idposts="", document="", token="";
    private SharedPreferences sessionManagement;
    private boolean checkUserPosts = false;
    private AlertDialog.Builder builder;

    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private PostsRetrofit postsRetrofit;

    public DialogMenuPosts(Context context, String idposts, String document, boolean checkUserPosts) {
        this.idposts = idposts;
        this.checkUserPosts = checkUserPosts;
        this.document= document;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        view = View.inflate(getContext(), R.layout.menu_posts, null);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        mapingView();
        if(checkUserPosts == false){

        }else{
            imgMenuPosts1.setImageResource(R.drawable.ic_edit_menu_posts_24);
            imgMenuPosts2.setImageResource(R.drawable.ic_lock_menu_posts_24);
            imgMenuPosts3.setImageResource(R.drawable.ic_baseline_delete_24);
            txtMenuPosts1.setText("Ch???nh s???a b??i vi???t");
            txtMenuPosts2.setText("Ch???nh s???a quy???n ri??ng t??");
            txtMenuPosts3.setText("X??a");
        }

        bottomSheetDialog.setContentView(view);
        return bottomSheetDialog;
    }
    private void mapingView(){
        linearMenuPosts1 = (LinearLayout) view.findViewById(R.id.linearMenuPosts1);
        linearMenuPosts2 = (LinearLayout) view.findViewById(R.id.linearMenuPosts2);
        linearMenuPosts3 = (LinearLayout) view.findViewById(R.id.linearMenuPosts3);
        imgMenuPosts1 = (ImageView) view.findViewById(R.id.imgMenuPosts1);
        imgMenuPosts2 = (ImageView) view.findViewById(R.id.imgMenuPosts2);
        imgMenuPosts3= (ImageView) view.findViewById(R.id.imgMenuPosts3);
        txtMenuPosts1 = (TextView) view.findViewById(R.id.txtMenuPosts1);
        txtMenuPosts2 = (TextView) view.findViewById(R.id.txtMenuPosts2);
        txtMenuPosts3 = (TextView) view.findViewById(R.id.txtMenuPosts3);

        linearMenuPosts1.setOnClickListener(this);
        linearMenuPosts2.setOnClickListener(this);
        linearMenuPosts3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(checkUserPosts != false){
            switch (v.getId()){
                case R.id.linearMenuPosts1:
                    editPosts();
                    break;
                case R.id.linearMenuPosts2:
                    break;
                case R.id.linearMenuPosts3:
                    showdialogDelete();
                    break;
            }
        }
    }
    private void editPosts(){
       DialogEditPosts dialogEditPosts = new DialogEditPosts(context, idposts, document);
       dialogEditPosts.show(getFragmentManager(), "edit posts");
        DialogMenuPosts.this.getDialog().cancel();
    }
    private void showdialogDelete(){
        builder = new AlertDialog.Builder(context);
        builder.setTitle("X??a b??i vi???t")
                .setMessage("B???n c?? ch???c ch???n mu???n x??a b??i vi???t n??y hay kh??ng?")
                // Add action buttons
                .setPositiveButton("X??a", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deletePosts(dialog);
                    }
                })
                .setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
        DialogMenuPosts.this.getDialog().cancel();
    }
    private void deletePosts(DialogInterface dialog){
        sessionManagement = context.getApplicationContext().getSharedPreferences("userlogin", Context.MODE_PRIVATE);
//        id = sessionManagement.getString("id","");
//        avata = sessionManagement.getString("avata", "");
//        coverimage = sessionManagement.getString("coverimage", "");
//        username = sessionManagement.getString("username","");
        token = "Bearer "+sessionManagement.getString("token","");
        postsRetrofit = retrofit.create(PostsRetrofit.class);
        Call<Error> errorCall = postsRetrofit.deletePosts(token, idposts);
        errorCall.enqueue(new Callback<Error>() {
            @Override
            public void onResponse(Call<Error> call, Response<Error> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, " l???i respone");
                }else{
                    Error error = response.body();
                    if(error.isSuccess()){
                        Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "X??a th???t b???i", Toast.LENGTH_SHORT).show();
                    }
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<Error> call, Throwable t) {
                Log.d(TAG, "l???i "+ t.getMessage());
                call.cancel();
            }
        });
        dialog.dismiss();
    }
}
