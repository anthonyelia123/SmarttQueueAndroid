package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

import me.queue.smartqueue.common.SuccessListener;

public class UpdateJoinAsync {

    public UpdateJoinAsync(String queueId, HashMap<String, Object> info, SuccessListener callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;
        db.collection("join").document(queueId)
                .update(info).addOnCompleteListener(task -> {
            callback.isSuccess(true);
        }).addOnFailureListener(e -> {
            callback.isSuccess(false);
        });

    }
}
