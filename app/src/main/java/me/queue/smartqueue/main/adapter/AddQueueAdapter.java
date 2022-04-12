package me.queue.smartqueue.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.queue.smartqueue.R;
import me.queue.smartqueue.createeditqueue.CreateEditActivity;
import me.queue.smartqueue.main.data.models.QueueModel;

public class AddQueueAdapter extends RecyclerView.Adapter<AddQueueAdapter.AddQueuesViewHolder> {
    private final ArrayList<QueueModel> queues;
    private final Context context;

    public AddQueueAdapter(ArrayList<QueueModel> queues,Context context) {
        this.queues = queues;
        this.context = context;
    }

    @NonNull
    @Override
    public AddQueuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_queues, parent, false);
        return new AddQueuesViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull AddQueuesViewHolder holder, int position) {
        QueueModel queueModel = queues.get(position);
        holder.tvQueueName.setText(queueModel.getQueueName());
        holder.btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(context, CreateEditActivity.class);
            i.putExtra("queue",queueModel);
            context.startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        return queues.size();
    }

    public static class AddQueuesViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvQueueName;
        private final Button btnEdit;

        public AddQueuesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQueueName = itemView.findViewById(R.id.tvQueueName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }


    }


}
