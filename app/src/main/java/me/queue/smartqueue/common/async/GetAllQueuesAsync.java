package me.queue.smartqueue.common.async;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import me.queue.smartqueue.main.data.models.LatLongModel;
import me.queue.smartqueue.main.data.models.QueueModel;

public class GetAllQueuesAsync {
    public GetAllQueuesAsync(GetAllQueuesCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("queues");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<QueueModel> queueModels = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    queueModels.add(new QueueModel(
                            document.getString("queueName"),
                            document.getString("queueId"),
                            document.getString("isFinished"),
                            document.getString("createdAt"),
                            document.getString("endedAt"),
                            document.getString("ownerId"),
                            new LatLongModel(
                                    document.getString("latitude"),
                                    document.getString("longitude")),
                            document.getString("maxSize"),
                            document.getString("mue"),
                            document.getString("lambda"),
                            document.getString("field"),
                            document.getString("counter"),
                            document.getString("finishedId")
                    ));
                }

                callback.getAllQueues(queueModels);
            }
        });
    }

    public interface GetAllQueuesCallback {
        void getAllQueues(ArrayList<QueueModel> queueModels);
    }
}
