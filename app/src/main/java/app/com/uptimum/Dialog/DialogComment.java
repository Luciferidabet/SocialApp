package app.com.uptimum.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import app.com.uptimum.R;
import app.com.uptimum.model.Error;
import app.com.uptimum.network.CommentRetrofit;
import app.com.uptimum.network.NetworkUtil;

public class DialogComment extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "DialogComment";
    private View view;
    private LinearLayout linearReplyCmt, linearCoppyCmt, linearEditCmt, linearDeleteCmt;
    private Context context;
    private String idcmt="", token ="", document="";
    private SharedPreferences sessionManagement;
    private CommentRetrofit commentRetrofit;
    private NetworkUtil networkUtil;
    private Retrofit retrofit;
    private TextView txtUserCmt;

    public DialogComment(Context context, String idcmt, String document, TextView txtUserCmt) {
        this.context = context;
        this.idcmt = idcmt;
        this.document = document;
        this.txtUserCmt = txtUserCmt;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        view = View.inflate(getContext(), R.layout.item_menu_comment, null);
        networkUtil = new NetworkUtil();
        retrofit = networkUtil.getRetrofit();
        mapingView();
        getDatalogin();
        bottomSheetDialog.setContentView(view);
        return bottomSheetDialog;
    }
    private void mapingView(){
        linearReplyCmt = (LinearLayout) view.findViewById(R.id.linearReplyCmt);
        linearCoppyCmt = (LinearLayout) view.findViewById(R.id.linearCoppyCmt);
        linearEditCmt = (LinearLayout) view.findViewById(R.id.linearEditCmt);
        linearDeleteCmt = (LinearLayout) view.findViewById(R.id.linearDeleteCmt);

        linearReplyCmt.setOnClickListener(this);
        linearCoppyCmt.setOnClickListener(this);
        linearEditCmt.setOnClickListener(this);
        linearDeleteCmt.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearEditCmt:
                dialogEditComment();
                break;
            case R.id.linearDeleteCmt:
                dialogDeleteComment();
                break;
        }
    }
    private void getDatalogin(){
        sessionManagement = context.getApplicationContext().getSharedPreferences("userlogin", Context.MODE_PRIVATE);
//        id = sessionManagement.getString("id","");
//        avata = sessionManagement.getString("avata", "");
//        coverimage = sessionManagement.getString("coverimage", "");
//        username = sessionManagement.getString("username","");
        token = "Bearer "+sessionManagement.getString("token","");
    }
    private void dialogEditComment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewedit = inflater.inflate(R.layout.dialog_edit_posts,null);
        TextView txtxTitle = (TextView) viewedit.findViewById(R.id.txtxTitle);
        EditText etEditPosts = (EditText) viewedit.findViewById(R.id.etEditPosts);
        etEditPosts.setText(document);
        txtxTitle.setText("Ch???nh s???a b??nh lu???n");
        builder.setView(viewedit)
                // Add action buttons
                .setPositiveButton("C???p nh???t", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        updateComment(etEditPosts.getText().toString());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
        DialogComment.this.getDialog().cancel();
    }
    private void updateComment(String documents){
        Log.d(TAG, "idcmt "+idcmt+documents);
        commentRetrofit = retrofit.create(CommentRetrofit.class);
        Call<Error> errorCall = commentRetrofit.putCmt(token, idcmt, documents);
        errorCall.enqueue(new Callback<Error>() {
            @Override
            public void onResponse(Call<Error> call, Response<Error> response) {
                if(!response.isSuccessful()) Log.d(TAG, "l???i response");
                else{
                    Error error = response.body();
                    if(error.isSuccess()) {
                        Log.d(TAG, " th??nh c??ng");
                        txtUserCmt.setText(documents);
                    }
                    else Log.d(TAG, "th???t b???i");
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<Error> call, Throwable t) {
                Log.d(TAG, " l???i "+t.getMessage());
                call.cancel();
            }
        });
    }
    private void dialogDeleteComment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("X??a b??nh lu???n")
                .setMessage("B???n c?? ch???c ch???n mu???n x??a b??nh lu???n n??y hay kh??ng?")
                // Add action buttons
                .setPositiveButton("X??a", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deletCmt();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
        DialogComment.this.getDialog().cancel();
    }
    private void deletCmt(){
        Log.d(TAG, "idcmt "+idcmt);
        commentRetrofit = retrofit.create(CommentRetrofit.class);
        Call<Error> errorCall = commentRetrofit.deleteCmt(token, idcmt);
        errorCall.enqueue(new Callback<Error>() {
            @Override
            public void onResponse(Call<Error> call, Response<Error> response) {
                if(!response.isSuccessful()) Log.d(TAG, "l???i response");
                else{
                    Error error = response.body();
                    if(error.isSuccess()) Log.d(TAG, " th??nh c??ng");
                    else Log.d(TAG, "th???t b???i");
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<Error> call, Throwable t) {
                Log.d(TAG, " l???i "+t.getMessage());
                call.cancel();
            }
        });
    }
}
