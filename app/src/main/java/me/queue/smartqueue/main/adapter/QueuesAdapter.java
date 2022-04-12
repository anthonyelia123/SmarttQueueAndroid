package me.queue.smartqueue.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;

import java.util.ArrayList;

import me.queue.smartqueue.R;
import me.queue.smartqueue.main.data.models.QueueModel;

public class QueuesAdapter extends RecyclerView.Adapter<QueuesAdapter.QueuesViewHolder> {
    private ArrayList<QueueModel> queues;
    private QueueClickListener queueClickListener;

    public QueuesAdapter(ArrayList<QueueModel> queues, QueueClickListener queueClickListener) {
        this.queues = queues;
        this.queueClickListener = queueClickListener;
    }

    @NonNull
    @Override
    public QueuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_queue, parent, false);
        return new QueuesViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull QueuesViewHolder holder, int position) {
        QueueModel queueModel = queues.get(position);

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class QueuesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ExpandableCardView ecqueue;

        public QueuesViewHolder(@NonNull View itemView) {
            super(itemView);
            ecqueue = itemView.findViewById(R.id.ecqueue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            queueClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface QueueClickListener {
        void onItemClick(View v, int position);
    }
}
