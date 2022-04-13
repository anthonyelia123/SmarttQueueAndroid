package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.queue.smartqueue.common.models.UserModel;

public class GetSpeceficUser {

    public GetSpeceficUser(String userId,GetCurrentUserCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                UserModel model = new UserModel(
                        task.getResult().getString("firstname"),
                        task.getResult().getString("lastname"),
                        task.getResult().getString("email"),
                        task.getResult().getString("password"),
                        task.getResult().getString("age"),
                        task.getResult().getString("mobile"),
                        task.getResult().getString("type")
                );
                callback.getUser(model);
            }
        });
    }


    public interface GetCurrentUserCallback {
        void getUser(UserModel models);
    }
}
