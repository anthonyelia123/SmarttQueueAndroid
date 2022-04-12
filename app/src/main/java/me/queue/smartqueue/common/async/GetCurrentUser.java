package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.queue.smartqueue.common.models.UserModel;

public class GetCurrentUser {

    public GetCurrentUser(GetCurrentUserCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.addSnapshotListener((value, error) -> {
            UserModel model = new UserModel(
                    value.getString("firstname"),
                    value.getString("lastname"),
                    value.getString("email"),
                    value.getString("password"),
                    value.getString("age"),
                    value.getString("mobile"),
                    value.getString("type")
            );
            callback.getUser(model);
        });
    }

    public interface GetCurrentUserCallback {

        void getUser(UserModel models);
    }
}
