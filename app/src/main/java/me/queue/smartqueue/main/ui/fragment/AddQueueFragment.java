package me.queue.smartqueue.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import me.queue.smartqueue.common.async.GetAllQueuesAsync;
import me.queue.smartqueue.createeditqueue.CreateEditActivity;
import me.queue.smartqueue.createeditqueue.QueuesAdapter;
import me.queue.smartqueue.databinding.FragmentAddQueueBinding;
import me.queue.smartqueue.main.adapter.AddQueueAdapter;
import me.queue.smartqueue.main.data.models.QueueModel;

public class AddQueueFragment extends Fragment {

    private FragmentAddQueueBinding binding;


    public AddQueueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.srlswipe.setRefreshing(true);
        binding.animationView.setVisibility(View.VISIBLE);
        binding.rvMyQueues.setVisibility(View.GONE);
        initRecycler();
        binding.addEditQueue.setOnClickListener(V -> {
            startActivity(new Intent(requireActivity(), CreateEditActivity.class));
        });
        binding.srlswipe.setOnRefreshListener(this::initRecycler);
    }

    private void initRecycler() {
        new GetAllQueuesAsync(queueModels -> {
            binding.srlswipe.setRefreshing(false);
            if (queueModels.size() > 0) {
                binding.animationView.setVisibility(View.GONE);
                binding.rvMyQueues.setVisibility(View.VISIBLE);
                ArrayList<QueueModel> filteredQ = new ArrayList<>();
                for (QueueModel q : queueModels) {
                    if (q.getOwnerId().equals(FirebaseAuth.getInstance().getUid()))
                        filteredQ.add(q);
                }
                if (!filteredQ.isEmpty()) {
                    AddQueueAdapter adapter = new AddQueueAdapter(queueModels, requireActivity());
                    binding.rvMyQueues.setHasFixedSize(true);
                    binding.rvMyQueues.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.rvMyQueues.setAdapter(adapter);
                }
            } else {
                //TODO: show empty list
                binding.animationView.setVisibility(View.VISIBLE);
                binding.rvMyQueues.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}