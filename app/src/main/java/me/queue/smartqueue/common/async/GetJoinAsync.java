package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import me.queue.smartqueue.common.UserJoinListener;
import me.queue.smartqueue.common.models.Users;


public class GetJoinAsync {
    public GetJoinAsync(String queueId, UserJoinListener callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;

        DocumentReference docRef = db.collection("join").document(queueId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getData() == null) {
                    callback.userInfo(null);
                } else {
                    Gson gsonObj = new Gson();
                    String jsonStr = gsonObj.toJson(task.getResult().getData());
                    Users process = gsonObj.fromJson(jsonStr, Users.class);
                    callback.userInfo(process);
                }
            }
        });
    }

}
