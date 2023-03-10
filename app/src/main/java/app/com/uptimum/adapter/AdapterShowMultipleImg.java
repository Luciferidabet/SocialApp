package app.com.uptimum.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.uptimum.R;

import static app.com.uptimum.util.Constants.BASE_URL;

public class AdapterShowMultipleImg extends RecyclerView.Adapter<AdapterShowMultipleImg.Viewhoder> {
    private ArrayList<String> UrlImg;
    private Context context;

    public AdapterShowMultipleImg(ArrayList<String> urlImg, Context context) {
        UrlImg = urlImg;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterShowMultipleImg.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_show_multi_img, parent,false);
        return new AdapterShowMultipleImg.Viewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShowMultipleImg.Viewhoder holder, int position) {
        Log.d("via", "1 "+UrlImg.get(position));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0
        );
        switch (UrlImg.size()){
            case 1:
                param.height = 1000;
                holder.itemShowMultiImg.setLayoutParams(param);
                Picasso.get().load(BASE_URL + "uploads/" + UrlImg.get(position))
                        .fit().centerCrop().into(holder.itemShowMultiImg);
                break;
            case 2:
                param.height = 1000 / 2;
                holder.itemShowMultiImg.setLayoutParams(param);
                Picasso.get().load(BASE_URL + "uploads/" + UrlImg.get(position))
                        .fit().centerCrop().into(holder.itemShowMultiImg);
                break;
            case 3:
                param.height = 1000 / 3;
                holder.itemShowMultiImg.setLayoutParams(param);
                Picasso.get().load(BASE_URL + "uploads/" + UrlImg.get(position))
                        .fit().centerCrop().into(holder.itemShowMultiImg);
                break;
            default:
                param.height = 1000 / 3;
                holder.itemShowMultiImg.setLayoutParams(param);
                Picasso.get().load(BASE_URL + "uploads/" + UrlImg.get(position))
                        .fit().centerCrop().into(holder.itemShowMultiImg);
                if(position == 2){
                    holder.rltloCountImg.setVisibility(View.VISIBLE);
                    int count = UrlImg.size() - 3;
                    holder.txtCountImg.setText("+ "+count);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return UrlImg.size();
    }

    @Override
    public void onViewRecycled(@NonNull Viewhoder holder) {
        super.onViewRecycled(holder);
        holder.cleanup();
    }

    public class Viewhoder extends RecyclerView.ViewHolder {
        private ImageView itemShowMultiImg;
        private TextView txtCountImg;
        private RelativeLayout rltloCountImg;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            itemShowMultiImg = (ImageView) itemView.findViewById(R.id.itemShowMultiImg);
            txtCountImg = (TextView) itemView.findViewById(R.id.txtCountImg);
            rltloCountImg = (RelativeLayout) itemView.findViewById(R.id.rltloCountImg);
        }
        public void cleanup() {
            Picasso.get().cancelRequest(itemShowMultiImg);
            itemShowMultiImg.setImageDrawable(null);
        }
    }
}
