package me.queue.smartqueue.userinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import java.util.ArrayList;
import java.util.List;
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
    private List<UserJoinStatus> allUsersModel;
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
                    allUsersModel = process.getUsers();
                    binding.lottieAnimationView.setVisibility(View.GONE);
                    binding.rvUsers.setVisibility(View.VISIBLE);

                    final int[] serverSelected = {1};
                    binding.spinnerServer.setVisibility(View.VISIBLE);
                    int nbOfServers = Integer.parseInt(queueModel.getCounter());
                    ArrayList<String> servers = new ArrayList<>();
                    for(int i = 0; i < nbOfServers; i++){
                        servers.add("server "+ ( i + 1));
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, servers);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerServer.setAdapter(arrayAdapter);
                    binding.spinnerServer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            serverSelected[0] = position + 1;
                            new GetJoinAsync(queueModel.getQueueId(), process2 -> {
                                List<UserJoinStatus> filteredUser = getUserDependingOnServer(process.getUsers(), serverSelected[0]);
                                initRecycler(filteredUser);
                            });

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            });

        }else {
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
            binding.rvUsers.setVisibility(View.GONE);
        }


    }


    private List<UserJoinStatus> getUserDependingOnServer(List<UserJoinStatus> all, int server){
        List<UserJoinStatus> result = new ArrayList<>();
        for(UserJoinStatus u: all){
            if(u.getServer() == server){
                result.add(u);
            }
        }
        return result;
    }

    private void initRecycler(List<UserJoinStatus> users){
        if(users != null){
            QueueInfoAdapter adapter = new QueueInfoAdapter((ArrayList<UserJoinStatus>) users,(ArrayList<UserJoinStatus>)allUsersModel, this, queueModel, new QueueInfoAdapter.Refresh() {
                @Override
                public void onRefresh() {
                    new GetJoinAsync(queueModel.getQueueId(), process -> {
                        if (process !=null){
                            initRecycler(process.getUsers());
                        }
                    });
                }
            });
            binding.rvUsers.setHasFixedSize(true);
            binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
            binding.rvUsers.setAdapter(adapter);
        }
    }
}