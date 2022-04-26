package me.queue.smartqueue.userinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import me.queue.smartqueue.R;
import me.queue.smartqueue.common.async.CheckBannedUsersAsync;
import me.queue.smartqueue.common.async.SetBannedUsersAsync;
import me.queue.smartqueue.common.models.UserIdModel;
import me.queue.smartqueue.common.models.UserModel;
import me.queue.smartqueue.login.LoginActivity;
import me.queue.smartqueue.main.data.models.QueueModel;
import me.queue.smartqueue.splash.SplashActivity;
import me.queue.utils.LocalFunctions;

public class QueueInfoAdapter extends RecyclerView.Adapter<QueueInfoAdapter.QueueInfoViewHolder> {
    private final ArrayList<UserIdModel> userModels;
    private final Context context;

    public QueueInfoAdapter(ArrayList<UserIdModel> userModels, Context context) {
        this.userModels = userModels;
        this.context = context;
    }

    @NonNull
    @Override
    public QueueInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_queue_info, parent, false);
        return new QueueInfoViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueInfoViewHolder holder, int position) {
        UserIdModel user = userModels.get(position);
        holder.tvUserName.setText(user.getFullName());
        holder.btnSkip.setOnClickListener(V->{
            new SetBannedUsersAsync(user.getUserId(),isSuccess->{
                if (isSuccess){
                    LocalFunctions.successToast(context,"user skipped");
                    new CheckBannedUsersAsync(user.getUserId(),call->{
                        if (call){
                            FirebaseAuth.getInstance().signOut();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    });
                }else {
                    LocalFunctions.errorToast(context,"error");
                }
            });
        });


    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public static class QueueInfoViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvUserName;
        private final Button btnSkip;

        public QueueInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            btnSkip = itemView.findViewById(R.id.btnSkip);
        }


    }
}
