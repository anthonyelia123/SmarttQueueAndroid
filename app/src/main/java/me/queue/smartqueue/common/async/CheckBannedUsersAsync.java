package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.queue.smartqueue.common.SuccessListener;
import me.queue.smartqueue.common.models.UserModel;

public class CheckBannedUsersAsync {
    public CheckBannedUsersAsync(String userId, SuccessListener callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("banned").document("banned");
        docRef.addSnapshotListener((value, error) -> {
            int counter =0;
            if (value!=null){
                String allUsers = value.getString("banned");

                if (allUsers!=null){
                    String[] parts = allUsers.split(",");
                    for (String id:parts){
                        if (id.equals(userId)){
                            counter++;
                        }
                    }
                }

            }
            callback.isSuccess(counter >= 3);
        });
    }
}
