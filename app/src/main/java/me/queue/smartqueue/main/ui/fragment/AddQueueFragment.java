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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import me.queue.smartqueue.common.async.GetAllQueuesAsync;
import me.queue.smartqueue.createeditqueue.CreateEditActivity;
import me.queue.smartqueue.createeditqueue.QueuesAdapter;
import me.queue.smartqueue.databinding.FragmentAddQueueBinding;
import me.queue.smartqueue.main.adapter.AddQueueAdapter;

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
        initRecycler();
        binding.addEditQueue.setOnClickListener(V ->{
            startActivity(new Intent(requireActivity(), CreateEditActivity.class));
        });
        binding.srlswipe.setOnRefreshListener(this::initRecycler);
    }

    private void initRecycler(){
        new GetAllQueuesAsync(queueModels -> {
            if (queueModels.size()>0){
                AddQueueAdapter adapter = new AddQueueAdapter(queueModels,requireActivity());
                binding.rvMyQueues.setHasFixedSize(true);
                binding.rvMyQueues.setLayoutManager(new LinearLayoutManager(requireActivity()));
                binding.rvMyQueues.setAdapter(adapter);
                binding.srlswipe.setRefreshing(false);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}