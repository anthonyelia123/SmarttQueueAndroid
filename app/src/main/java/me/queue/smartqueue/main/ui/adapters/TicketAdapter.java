package me.queue.smartqueue.main.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import me.queue.smartqueue.Model.QueueAndServiceTime;
import me.queue.smartqueue.R;
import me.queue.smartqueue.common.async.GetJoinAsync;
import me.queue.smartqueue.common.async.SetJoinAsync;
import me.queue.smartqueue.common.async.UpdateJoinAsync;
import me.queue.smartqueue.common.async.UpdateQueueAsync;
import me.queue.smartqueue.common.models.UserJoinStatus;
import me.queue.smartqueue.common.models.Users;
import me.queue.smartqueue.main.data.models.QueueModel;
import me.queue.utils.BitmapUtils;
import me.queue.utils.LocalFunctions;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private final ArrayList<QueueModel> queues;
    private final Context context;
    private TicketListener listener;
    private String userId;

    public TicketAdapter(ArrayList<QueueModel> queues, Context context, TicketListener listener) {
        this.queues = queues;
        this.context = context;
        this.listener = listener;
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        try {
            QueueModel current = queues.get(position);
            holder.ivCode.setImageBitmap(BitmapUtils.CreateImage(current.getOwnerId(), "Barcode"));

            new GetJoinAsync(current.getQueueId(), process -> {
                if(current.isAdmin){
                    if(process != null){
                        holder.tvQueuing.setText("Queuing People: " + getWaitingPeople(process.getUsers()));
                    }
                    holder.btnNext.setVisibility(View.VISIBLE);
                    holder.btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(process != null && process.getUsers() != null && getWaitingPeople(process.getUsers()) > 0){
                                HashMap<String, Object> hash = new HashMap<>();
                                String queueOwner = process.getQueueOwner();
                                List<UserJoinStatus> userJoinStatus = process.getUsers();
                                String uId = "";
                                int count = 0;
                                for(UserJoinStatus curr : userJoinStatus){
                                    if(!curr.isFinished() && count == 0){
                                        curr.setFinished(true);
                                        uId = curr.getUserId();
                                        count++;
                                    }
                                    curr.setDelayed(false);
                                    curr.setJoinDate(LocalDateTime.now().toString());
                                }
                                hash.put("queueOwner", queueOwner);
                                hash.put("users", userJoinStatus);
                                String finalUId = uId;
                                new UpdateJoinAsync(current.getQueueId(), hash, result ->{
                                    HashMap<String, Object> hash2 = new HashMap<>();
                                    hash2.put("counter", current.getCounter());
                                    hash2.put("createdAt", current.getCreatedAt());
                                    hash2.put("endedAt", current.getEndedAt());
                                    hash2.put("field", current.getField());
                                    hash2.put("isFinished", current.getIsFinished());
                                    hash2.put("location", current.getLocation());
                                    hash2.put("maxSize", current.getMaxSize());
                                    hash2.put("mue", current.getMue());
                                    hash2.put("ownerId", current.getOwnerId());
                                    hash2.put("queueId", current.getQueueId());
                                    hash2.put("queueName", current.getQueueName());
                                    hash2.put("lambda", current.getLambda());
                                    String joinedId = current.getFinishedId();
                                    String[] joinedIdsArray = joinedId.split(",");
                                    String remainingIds = "";
                                    for(String s : joinedIdsArray){
                                        if(!s.equals(finalUId)){
                                            remainingIds += s + ",";
                                        }
                                    }
                                    hash2.put("finishedId", remainingIds);
                                    new UpdateQueueAsync(current.getQueueId(), hash2, result2->{
                                        resetQueue(process, current.getQueueId(), current);
                                    });
                                });
                            }
                        }
                    });
                } else {
                    holder.btnJoin.setVisibility(View.VISIBLE);
                    holder.btnQuit.setOnClickListener(V ->{
                        HashMap<String, Object> hash2 = new HashMap<>();
                        hash2.put("counter", current.getCounter());
                        hash2.put("createdAt", current.getCreatedAt());
                        hash2.put("endedAt", current.getEndedAt());
                        hash2.put("field", current.getField());
                        hash2.put("isFinished", current.getIsFinished());
                        hash2.put("location", current.getLocation());
                        hash2.put("maxSize", current.getMaxSize());
                        hash2.put("mue", current.getMue());
                        hash2.put("ownerId", current.getOwnerId());
                        hash2.put("queueId", current.getQueueId());
                        hash2.put("queueName", current.getQueueName());
                        hash2.put("lambda", current.getLambda());
                        String joinedId = current.getFinishedId();
                        String[] joinedIdsArray = joinedId.split(",");
                        StringBuilder remainingIds = new StringBuilder();
                        for(String s : joinedIdsArray){
                            if(!s.equals(userId)){
                                remainingIds.append(s).append(",");
                            }
                        }
                        hash2.put("finishedId", remainingIds.toString());
                        new UpdateQueueAsync(current.getQueueId(), hash2, result2->{
                            removeCurrentUserFromQueue(process, current.getQueueId());
                        });
                    });
                    holder.btnJoin.setOnClickListener(V -> {

                            HashMap<String, Object> hash2 = new HashMap<>();
                            hash2.put("counter", current.getCounter());
                            hash2.put("createdAt", current.getCreatedAt());
                            hash2.put("endedAt", current.getEndedAt());
                            hash2.put("field", current.getField());
                            hash2.put("isFinished", current.getIsFinished());
                            hash2.put("location", current.getLocation());
                            hash2.put("maxSize", current.getMaxSize());
                            hash2.put("mue", current.getMue());
                            hash2.put("ownerId", current.getOwnerId());
                            hash2.put("queueId", current.getQueueId());
                            hash2.put("queueName", current.getQueueName());
                            hash2.put("lambda", current.getLambda());
                            String joinedId = current.getFinishedId();
                            joinedId += userId + ",";
                            hash2.put("finishedId", joinedId);
                            new UpdateQueueAsync(current.getQueueId(), hash2, result2->{
                                listener.onJoin(current);
                            });
                    });

                    if(process != null){
                        QueueAndServiceTime queueAndServiceTime = LocalFunctions.getQueueTime(current);
                        double waitingTime = 0.0;
                        long remainingTime = 0;

                        if(isDelayed(process.getUsers())){
                            holder.tvWaiting.setText("Waiting Time: Delayed");
                            holder.tvWaiting.setTextColor(Color.RED);
                        } else {
                            waitingTime = getWaitingTime(queueAndServiceTime, process.getUsers());
                            String joinTimeStr = getJoinDate(process.getUsers());
                            remainingTime = getRemainingMin(waitingTime, joinTimeStr);
                            if(remainingTime <= 0 && waitingTime > 0){
                                holder.tvWaiting.setText("Waiting Time: Delayed");
                                holder.tvWaiting.setTextColor(Color.RED);
                                setAllDelayed(process, current.getQueueId());
                            } else {
                                if(waitingTime == 0){
                                    holder.tvWaiting.setText("Waiting Time: " + 0 + " mins");
                                } else {
                                    holder.tvWaiting.setText("Waiting Time: " + remainingTime + " mins");
                                }
                            }
                        }
                        holder.tvQueuing.setText("Queuing People: " + getWaitingPeople(process.getUsers()));
                        for (UserJoinStatus statuses : process.getUsers()) {
                            if (statuses.getUserId().equals(userId)) {
                                holder.btnJoin.setVisibility(View.GONE);
                                holder.btnQuit.setVisibility(View.VISIBLE);
                                holder.btnQuit.setStrokeColor(ColorStateList.valueOf(Color.RED));
                            }
                        }
                    }
                }
                LocalDateTime today = LocalDateTime.parse(current.getCreatedAt());
                holder.tvEstimation.setText(today.getDayOfMonth()+"/"+today.getMonth()+"/"+today.getYear());
                holder.tvMax.setText("Max capacity: " + current.getMaxSize());
                holder.txt_code.setText(current.getQueueName());

            });
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return queues.size();
    }

    private void setAllDelayed(Users users, String queueId) {
        List<UserJoinStatus> usersJoin = users.getUsers();
        for(UserJoinStatus u: usersJoin){
            if(!u.isFinished()){
                u.setDelayed(true);
            }
        }
        users.setUsers(usersJoin);
        HashMap<String, Object> map = new HashMap<>();
        map.put("queueOwner", users.getQueueOwner());
        map.put("users", usersJoin);
        new UpdateJoinAsync(queueId, map, success -> {

        });
    }

    private void removeCurrentUserFromQueue(Users users, String queueId){
        List<UserJoinStatus> usersJoin = users.getUsers();
        List<UserJoinStatus> newUsersJoin = new ArrayList<>();
        for(UserJoinStatus u: usersJoin){
            if(!u.getUserId().equals(userId)){
                newUsersJoin.add(u);
            }
        }
        users.setUsers(usersJoin);
        HashMap<String, Object> map = new HashMap<>();
        map.put("queueOwner", users.getQueueOwner());
        map.put("users", newUsersJoin);
        new UpdateJoinAsync(queueId, map, success -> {
            listener.onJoin(null);
        });
    }

    private void resetQueue(Users users, String queueId, QueueModel current) {
        List<UserJoinStatus> usersJoin = users.getUsers();
        List<UserJoinStatus> newUsersJoin = new ArrayList<>();
        for(UserJoinStatus u: usersJoin){
            if(!u.isFinished()){
                newUsersJoin.add(u);
            }
        }
        users.setUsers(usersJoin);
        HashMap<String, Object> map = new HashMap<>();
        map.put("queueOwner", users.getQueueOwner());
        map.put("users", newUsersJoin);
        new UpdateJoinAsync(queueId, map, success -> {
            listener.onJoin(null);
        });
        /*List<UserJoinStatus> usersJoin = users.getUsers();
        for(UserJoinStatus u: usersJoin){
            if(!u.isFinished()){
                u.setDelayed(false);
                u.setJoinDate(LocalDateTime.now().toString());
            }
        }
        users.setUsers(usersJoin);
        HashMap<String, Object> map = new HashMap<>();
        map.put("queueOwner", users.getQueueOwner());
        map.put("users", usersJoin);
        new UpdateJoinAsync(queueId, map, success -> {

        });*/
        /*if(isDelayed(users.getUsers())){
            List<UserJoinStatus> usersJoin = users.getUsers();
            for(UserJoinStatus u: usersJoin){
                if(!u.isFinished()){
                    u.setDelayed(false);
                    u.setJoinDate(LocalDateTime.now().toString());
                }
            }
            users.setUsers(usersJoin);
            HashMap<String, Object> map = new HashMap<>();
            map.put("queueOwner", users.getQueueOwner());
            map.put("users", usersJoin);
            new UpdateJoinAsync(queueId, map, success -> {

            });
        } else {
            String joiningTimeStr = users.getJoiningTime();
            LocalDateTime joiningTime = LocalDateTime.parse(joiningTimeStr);
            ZonedDateTime zdtJoinDate = ZonedDateTime.of(joiningTime, ZoneId.systemDefault());
            long joiningTimeInMilliseconds = zdtJoinDate.toInstant().toEpochMilli();
            LocalDateTime currentDate = LocalDateTime.now();
            ZonedDateTime zdtJoinDateCurrent = ZonedDateTime.of(currentDate, ZoneId.systemDefault());
            long joiningTimeCurrentInMilliseconds = zdtJoinDateCurrent.toInstant().toEpochMilli();
            long timePassed= joiningTimeCurrentInMilliseconds - joiningTimeInMilliseconds;
            long wsMillis = TimeUnit.MINUTES.toMillis((long) ws);
            long remainingTime = wsMillis - timePassed;
            if(remainingTime > 0) {

                List<UserJoinStatus> usersJoin = users.getUsers();
                for(UserJoinStatus u: usersJoin){
                    if(!u.isFinished()){
                        String s = users.getJoiningTime();
                        LocalDateTime dt = LocalDateTime.parse(s);
                        ZonedDateTime zdtdt = ZonedDateTime.of(dt, ZoneId.systemDefault());
                        long TimeInMilliseconds = zdtdt.toInstant().toEpochMilli();
                        long milliseconds = remainingTime + TimeInMilliseconds;
                        LocalDateTime date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
                        u.setJoinDate(date.toString());
                    }
                }
                users.setUsers(usersJoin);
                HashMap<String, Object> map = new HashMap<>();
                map.put("queueOwner", users.getQueueOwner());
                map.put("users", usersJoin);
                new UpdateJoinAsync(queueId, map, success -> {

                });
            }
        }*/

    }

    private String getJoinDate(List<UserJoinStatus> userJoinStatuses) {
        for(UserJoinStatus u: userJoinStatuses){
            if(u.getUserId().equals(userId) && !u.isFinished()){
                return u.getJoinDate();
            }
        }
        return "";
    }

    private boolean isDelayed(List<UserJoinStatus> userJoinStatuses) {
        for(UserJoinStatus cur : userJoinStatuses){
            if(cur.isDelayed() && !cur.isFinished()){
                return true;
            }
        }
        return false;
    }

    private long getRemainingMin(double ws, String joinDateStr){
        if(joinDateStr.equals("")){
            return (long) ws;
        }
        LocalDateTime joinDate = LocalDateTime.parse(joinDateStr);
        LocalDateTime currentDate = LocalDateTime.now();
        ZonedDateTime zdtJoinDate = ZonedDateTime.of(joinDate, ZoneId.systemDefault());
        ZonedDateTime zdtCurrentDate = ZonedDateTime.of(currentDate, ZoneId.systemDefault());
        long joinDateInMilliseconds = zdtJoinDate.toInstant().toEpochMilli();
        long currentDateInMilliseconds = zdtCurrentDate.toInstant().toEpochMilli();
        long TimePassed = currentDateInMilliseconds - joinDateInMilliseconds;
        long wsMillis = TimeUnit.MINUTES.toMillis((long) ws);
        long remainingMilliSeconds = wsMillis - TimePassed;
        long remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingMilliSeconds);
        return remainingMinutes;
    }

    private int getWaitingPeople(List<UserJoinStatus> userJoinStatuses) {
        int sum = 0;
        for(UserJoinStatus u: userJoinStatuses){
            if(!u.isFinished())
                sum++;
        }
        return sum;
    }
    private double getWaitingTime(QueueAndServiceTime queueAndServiceTime, List<UserJoinStatus> userJoinStatuses){
        double time = 0.0;
        for(int i = 0; i < userJoinStatuses.size(); i++){
            UserJoinStatus currentUser = userJoinStatuses.get(i);
            if(currentUser.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                return time;
            }
            if(!currentUser.isFinished()){
                time += queueAndServiceTime.getWaitingService();
            }
        }
        return time;
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCode;
        private MaterialButton btnJoin, btnNext, btnQuit;
        private TextView tvEstimation,tvWaiting,tvMax,tvQueuing, txt_code;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCode = itemView.findViewById(R.id.ivCode);
            btnJoin = itemView.findViewById(R.id.btnJoin);
            tvEstimation = itemView.findViewById(R.id.tvEstimation);
            tvWaiting = itemView.findViewById(R.id.tvWaiting);
            tvMax = itemView.findViewById(R.id.tvMax);
            tvQueuing = itemView.findViewById(R.id.tvQueuing);
            btnNext = itemView.findViewById(R.id.btnNext);
            txt_code = itemView.findViewById(R.id.txt_code);
            btnQuit = itemView.findViewById(R.id.btnQuit);
        }
    }

    public interface TicketListener {
        void onJoin(QueueModel queue);
    }
}
