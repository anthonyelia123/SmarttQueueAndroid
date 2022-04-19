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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.queue.smartqueue.R;
import me.queue.smartqueue.common.async.CheckBannedUsersAsync;
import me.queue.smartqueue.common.async.SetBannedUsersAsync;
import me.queue.smartqueue.common.async.UpdateJoinAsync;
import me.queue.smartqueue.common.async.UpdateQueueAsync;
import me.queue.smartqueue.common.models.UserIdModel;
import me.queue.smartqueue.common.models.UserJoinStatus;
import me.queue.smartqueue.common.models.UserModel;
import me.queue.smartqueue.common.models.Users;
import me.queue.smartqueue.login.LoginActivity;
import me.queue.smartqueue.main.data.models.QueueModel;
import me.queue.utils.LocalFunctions;

public class QueueInfoAdapter extends RecyclerView.Adapter<QueueInfoAdapter.QueueInfoViewHolder> {
    private final ArrayList<UserJoinStatus> userModels;
    private final ArrayList<UserJoinStatus> alluserModels;
    private final Context context;
    private final QueueModel queueModel;
    private String userId = FirebaseAuth.getInstance().getUid();
    private Refresh listener;

    public QueueInfoAdapter(ArrayList<UserJoinStatus> userModels,ArrayList<UserJoinStatus> alluserModels, Context context, QueueModel queueModel, Refresh listener) {
        this.userModels = userModels;
        this.context = context;
        this.alluserModels = alluserModels;
        this.queueModel = queueModel;
        this.listener = listener;
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
        UserJoinStatus user = userModels.get(position);
        holder.tvUserName.setText(user.getFirstName() + " " + user.getLastName());
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
        holder.btnNext.setOnClickListener(V->{
            if( !userModels.isEmpty() ){
                HashMap<String, Object> hash = new HashMap<>();
                String queueOwner = userId;
                List<UserJoinStatus> userJoinStatus = alluserModels;
                String uId = "";
                boolean found = false;
                for(UserJoinStatus curr : userJoinStatus){
                    if( curr.getUserId().equals(user.getUserId())){
                        curr.setFinished(true);
                        uId = curr.getUserId();
                        found = true;
                    }
                    if(found){
                        curr.setDelayed(false);
                        curr.setJoinDate(LocalDateTime.now().toString());
                    }

                }
                hash.put("queueOwner", queueOwner);
                hash.put("users", userJoinStatus);
                String finalUId = uId;
                new UpdateJoinAsync(queueModel.getQueueId(), hash, result ->{
                    HashMap<String, Object> hash2 = new HashMap<>();
                    hash2.put("counter", queueModel.getCounter());
                    hash2.put("createdAt", queueModel.getCreatedAt());
                    hash2.put("endedAt", queueModel.getEndedAt());
                    hash2.put("field", queueModel.getField());
                    hash2.put("isFinished", queueModel.getIsFinished());
                    hash2.put("location", queueModel.getLocation());
                    hash2.put("maxSize", queueModel.getMaxSize());
                    hash2.put("mue", queueModel.getMue());
                    hash2.put("ownerId", queueModel.getOwnerId());
                    hash2.put("queueId", queueModel.getQueueId());
                    hash2.put("queueName", queueModel.getQueueName());
                    hash2.put("lambda", queueModel.getLambda());
                    String joinedId = queueModel.getJoiningId();
                    String[] joinedIdsArray = joinedId.split(",");
                    String remainingIds = "";
                    for(String s : joinedIdsArray){
                        if(!s.equals(finalUId)){
                            remainingIds += s + ",";
                        }
                    }
                    hash2.put("joiningId", remainingIds);
                    new UpdateQueueAsync(queueModel.getQueueId(), hash2, result2->{
                        resetQueue(userModels, queueModel.getQueueId(), queueModel);
                    });
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    private String getCurrentUserId(){
        for(UserJoinStatus u : userModels){
            if(!u.isFinished()){
                return u.getUserId();
            }
        }
        return "";
    }

    private void resetQueue(List<UserJoinStatus> usersJoin, String queueId, QueueModel current) {

        List<UserJoinStatus> newUsersJoin = new ArrayList<>();
        for(UserJoinStatus u: alluserModels){
            if(!u.isFinished()){
                newUsersJoin.add(u);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("queueOwner", userId);
        map.put("users", newUsersJoin);
        new UpdateJoinAsync(queueId, map, success -> {
            listener.onRefresh();
        });

    }

    public static class QueueInfoViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvUserName;
        private final Button btnSkip, btnNext;

        public QueueInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            btnSkip = itemView.findViewById(R.id.btnSkip);
            btnNext = itemView.findViewById(R.id.btnNext);
        }


    }
    public interface Refresh{
        void onRefresh();
    }
}
