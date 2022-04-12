package me.queue.smartqueue.userinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.zip.Inflater;

import me.queue.smartqueue.R;
import me.queue.smartqueue.common.UserJoinListener;
import me.queue.smartqueue.common.async.GetJoinAsync;
import me.queue.smartqueue.common.async.GetSpeceficUser;
import me.queue.smartqueue.common.async.GetUserDataAsync;
import me.queue.smartqueue.common.models.UserIdModel;
import me.queue.smartqueue.common.models.UserJoinStatus;
import me.queue.smartqueue.common.models.Users;
import me.queue.smartqueue.databinding.ActivityIntroBinding;
import me.queue.smartqueue.databinding.ActivityQueueInfoBinding;
import me.queue.smartqueue.main.adapter.AddQueueAdapter;
import me.queue.smartqueue.main.data.models.QueueModel;

public class QueueInfoActivity extends AppCompatActivity {
    private ActivityQueueInfoBinding binding;
    private QueueModel queueModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQueueInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lottieAnimationView.setVisibility(View.VISIBLE);
        binding.rvUsers.setVisibility(View.GONE);
        queueModel = (QueueModel) getIntent().getSerializableExtra("queue");
        if (queueModel != null) {
            new GetJoinAsync(queueModel.getQueueId(), process -> {
                if (process !=null){
                    binding.lottieAnimationView.setVisibility(View.GONE);
                    binding.rvUsers.setVisibility(View.VISIBLE);
                    getUsersFiltered(process);
                }
            });

        }else {
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
            binding.rvUsers.setVisibility(View.GONE);
        }


    }

    private void getUsersFiltered(Users users){

        new GetUserDataAsync(call->{
            ArrayList<UserIdModel> filtered = new ArrayList<>();
            for (int i=0;i<users.getUsers().size();i++){
                filtered.add(new UserIdModel(users.getUsers().get(i).getUserId(),
                        call.get(i).getFirstname()+" "+call.get(i).getLastname()));
            }
            QueueInfoAdapter adapter = new QueueInfoAdapter(filtered,this);
            binding.rvUsers.setHasFixedSize(true);
            binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
            binding.rvUsers.setAdapter(adapter);
        });
    }
}