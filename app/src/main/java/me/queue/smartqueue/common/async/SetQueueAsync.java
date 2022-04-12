package me.queue.smartqueue.common.async;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

import me.queue.smartqueue.common.SuccessListener;

public class SetQueueAsync {

//    public SetQueueAsync(Map<String, Object> fieldsData, SuccessListener callback) {
//
//        CollectionReference documentReference = FirebaseFirestore.getInstance().collection("queues");
//        documentReference.add(fieldsData)
//                .addOnSuccessListener(aVoid -> callback.isSuccess(true))
//                .addOnFailureListener(e -> callback.isSuccess(false));
//    }

    public SetQueueAsync(Map<String, Object> fieldsData, String queueId, SuccessListener callback) {

        FirebaseFirestore.getInstance().collection("queues").document(queueId)
                .set(fieldsData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> callback.isSuccess(true))
                .addOnFailureListener(e -> callback.isSuccess(false));
    }
}
