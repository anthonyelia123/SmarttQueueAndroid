package me.queue.smartqueue.main.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import me.queue.smartqueue.common.async.GetAllQueuesAsync;
import me.queue.smartqueue.common.async.GetJoinAsync;
import me.queue.smartqueue.common.async.GetSpeceficUser;
import me.queue.smartqueue.common.async.SetJoinAsync;
import me.queue.smartqueue.common.models.Users;
import me.queue.smartqueue.common.models.UserJoinStatus;
import me.queue.smartqueue.databinding.FragmentQueuesBinding;
import me.queue.smartqueue.main.data.models.QueueModel;
import me.queue.smartqueue.main.ui.adapters.TicketAdapter;

public class QueuesFragment extends Fragment {
    private FragmentQueuesBinding binding;
    private String userId;

    public QueuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQueuesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.srRefresh.setRefreshing(true);
        setupAdapter();
        binding.srRefresh.setOnRefreshListener(() -> {
            binding.srRefresh.setRefreshing(true);
            setupAdapter();
        });
        final Handler handler = new Handler();
        // Do something after 5s = 5000ms
        handler.postDelayed(this::setupAdapter, 30000);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void setupAdapter() {
        new GetAllQueuesAsync(queueModels -> {
             adapter = new TicketAdapter(filterList(queueModels), requireActivity(), queue -> {
                if (queue != null) {
                    new GetJoinAsync(queue.getQueueId(), process -> {
                        ArrayList<UserJoinStatus> join;
                        int server = getCorrespondingServer(process, Integer.parseInt(queue.getCounter()));
                        if (process != null) {
                            join = new ArrayList<>(process.getUsers());
                            join.add(new UserJoinStatus(
                                    LocalDateTime.now().toString(),
                                    false,
                                    userId,
                                    false,
                                    server,
                                    firstName,
                                    lastName
                            ));
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("queueOwner", queue.getOwnerId());
                            map.put("users", join);
                            map.put("joiningTime", process.getJoiningTime());
                            new SetJoinAsync(queue.getQueueId(), map, success -> {
                                Toasty.success(requireContext(), "Joined Successfully", Toasty.LENGTH_LONG).show();
                                setupAdapter();
                            });
                        } else {
                            join = new ArrayList<>();
                            join.add(new UserJoinStatus(
                                    LocalDateTime.now().toString(),
                                    false,
                                    userId,
                                    false,
                                    server,
                                    firstName,
                                    lastName
                            ));
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("queueOwner", queue.getOwnerId());
                            map.put("users", join);
                            map.put("joiningTime", LocalDateTime.now().toString());
                            new SetJoinAsync(queue.getQueueId(), map, success -> {
                                Toasty.success(requireContext(), "Joined Successfully", Toasty.LENGTH_LONG).show();
                                setupAdapter();
                            });
                        }
                    });
                } else {
                    setupAdapter();
                }


            });
            binding.rvTicket.setHasFixedSize(true);
            binding.rvTicket.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.rvTicket.setAdapter(adapter);
            binding.srRefresh.setRefreshing(false);
        });
    }

    private int getCorrespondingServer(Users joinModel, int serverNb){
        if(joinModel == null){
            return 1;
        }
        int[] servers = new int[serverNb];
        for(UserJoinStatus userJoining: joinModel.getUsers()){
            int server = userJoining.getServer();
            if(server > 0){
                servers[server - 1]++;
            }
        }
        int minimumServer = servers[0];
        int serverToReturn = 1;
        for(int i = 0; i< servers.length; i++){
            if(servers[i] < minimumServer){
                minimumServer = servers[i];
                serverToReturn = i + 1;
            }
        }
        return serverToReturn;
    }

    private ArrayList<QueueModel> filterList(ArrayList<QueueModel> all) {
        ArrayList<QueueModel> filteredQueues = new ArrayList<>();
        ArrayList<QueueModel> priorityQueues = new ArrayList<>();
        ArrayList<QueueModel> remainingQueues = new ArrayList<>();
        for (QueueModel queue : all) {
            if (queue.getOwnerId().equals(userId)) {
                queue.isAdmin = true;
            }
            try {
                if (queue.getJoiningId().contains(userId)) {
                    priorityQueues.add(queue);
                } else {
                    remainingQueues.add(queue);
                }
            } catch (Exception e) {
                remainingQueues.add(queue);
            }

        }
        if (priorityQueues.size() > 0)
            filteredQueues.addAll(priorityQueues);
        if (remainingQueues.size() > 0)
            filteredQueues.addAll(remainingQueues);
        return filteredQueues;
    }


    private boolean checkUser(ArrayList<String> allUsers) {
        for (String user : allUsers) {
            if (user.equals(userId))
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}