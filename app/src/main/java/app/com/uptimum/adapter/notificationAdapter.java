package app.com.uptimum.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import app.com.uptimum.Date.Timeupload;
import app.com.uptimum.R;
import app.com.uptimum.model.ListNotification;

import static app.com.uptimum.util.Constants.BASE_URL;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {
    private ArrayList<ListNotification> listNotifications;
    private Context context;
    private Timeupload date = new Timeupload();
    public notificationAdapter(ArrayList<ListNotification> listNotifications, Context context) {
        this.listNotifications = listNotifications;
        this.context = context;
    }

    @NonNull
    @Override
    public notificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notification, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.ViewHolder holder, int position) {
        holder.txtTimeNotify.setText(date.time(listNotifications.get(position).getCreateAt()));
        Picasso.get().load(BASE_URL+"uploads/"+listNotifications.get(position).getIduserNotify().getAvata()).into(holder.avataUserNotify);
        if(!listNotifications.get(position).isStatus()){
            // chưa xem
            holder.RelativeNotify.setBackgroundColor(ContextCompat.getColor(context, R.color.notificationCheck));
        }

        if(listNotifications.get(position).getTitle().equals("comment")){
            Picasso.get().load(R.drawable.icon_comment_notification).into(holder.imgStatusNotify);
            String documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b> "
                    +"<font color=#505050> đã bình luận bài viết của </font><b>"+ listNotifications.get(position).getIdPosts().getIduser().getUsername()+ "</b> ";
            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }else if(listNotifications.get(position).getTitle().equals("likeposts")){
            String documentNotify = "";
            if(listNotifications.get(position).getTypeLike() == 0){
                documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b>"
                        +"<font color=#505050> đã thích bài viết của bạn</font>";
            }else{
                documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b>"
                        +"<font color=#505050> đã bày tỏ cảm xúc về bài viết của bạn</font>";
            }
            Picasso.get().load(typeEmotionName(listNotifications.get(position).getTypeLike())).into(holder.imgStatusNotify);

            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }else if(listNotifications.get(position).getTitle().equals("follow")){
            Picasso.get().load(R.drawable.icon_followe_notification).into(holder.imgStatusNotify);
            String documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b>"
                    +"<font color=#505050> đã bắt đầu theo dõi bạn</font>";
            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }else if(listNotifications.get(position).getTitle().equals("replycomment")){
            Picasso.get().load(R.drawable.icon_comment_notification).into(holder.imgStatusNotify);
            String documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b> "
                    +"<font color=#505050> đã trả lời bình luận của </font>";
            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }else if(listNotifications.get(position).getTitle().equals("shareposts")){
            Picasso.get().load(R.drawable.ic_share_notification).into(holder.imgStatusNotify);
            String documentNotify = "<b>" + listNotifications.get(position).getIduserNotify().getUsername() + "</b> "
                    +"<font color=#505050> đã chia sẽ bài viết của bạn </font>";
            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }else if(listNotifications.get(position).getTitle().equals("addfriendsuccess")){
            Picasso.get().load(R.drawable.ic_pload_tabfriend).into(holder.imgStatusNotify);
            String documentNotify = "<b> Bạn và" + listNotifications.get(position).getIduserNotify().getUsername() + "</b> "
                    +"<font color=#505050> đã trở thành bạn bè</font>";
            holder.txtDocumentNotify.setText(Html.fromHtml(documentNotify));
        }
        // còn new post vs new story
        holder.imgMenuNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menu
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNotifications.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView avataUserNotify, imgStatusNotify;
        private ImageView imgMenuNotify;
        private TextView txtDocumentNotify, txtTimeNotify;
        private RelativeLayout RelativeNotify;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avataUserNotify = (CircleImageView) itemView.findViewById(R.id.avataUserNotify);
            imgStatusNotify = (CircleImageView) itemView.findViewById(R.id.imgStatusNotify);
            imgMenuNotify = (ImageView) itemView.findViewById(R.id.imgMenuNotify);
            txtDocumentNotify = (TextView) itemView.findViewById(R.id.txtDocumentNotify);
            txtTimeNotify = (TextView) itemView.findViewById(R.id.txtTimeNotify);
            RelativeNotify = (RelativeLayout) itemView.findViewById(R.id.RelativeNotify);
        }
        private void clear(){
            Picasso.get().cancelRequest(avataUserNotify);
            avataUserNotify.setImageDrawable(null);
            Picasso.get().cancelRequest(imgStatusNotify);
            imgStatusNotify.setImageDrawable(null);
        }
    }
    private int typeEmotionName(int typeEmotion){
        int emotion = 0;
        switch (typeEmotion){
            case 0:
                emotion = R.drawable.icon_like_facebook;
                break;
            case 1:
                emotion = R.drawable.ic_love_png;
                break;
            case 2:
                emotion = R.drawable.ic_care_png;
                break;
            case 3:
                emotion = R.drawable.ic_haha_png;
                break;
            case 4:
                emotion = R.drawable.ic_wow_png;
                break;
            case 5:
                emotion = R.drawable.ic_sad_png;
                break;
            case 6:
                emotion = R.drawable.ic_angry_png;
                break;
            default:
                emotion = R.drawable.icon_dislike;
        }
        return emotion;
    }
}
