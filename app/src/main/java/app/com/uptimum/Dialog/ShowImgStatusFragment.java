package app.com.uptimum.Dialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.zolad.zoominimageview.ZoomInImageView;

import app.com.uptimum.R;

import static app.com.uptimum.util.Constants.BASE_URL;

public class ShowImgStatusFragment extends Fragment {
    private final String TAG = "ShowImgStatusFragment";
    private View view;
    private ZoomInImageView imgdialog;
    private String UrlImg;

    public ShowImgStatusFragment(String urlImg) {
        UrlImg = urlImg;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_img_status, container, false);
        imgdialog = (ZoomInImageView) view.findViewById(R.id.imgdialog);
        Log.d(TAG, " "+UrlImg);
        Picasso.get().load(BASE_URL+"uploads/"+UrlImg).into(imgdialog);
        return view;
    }
}
