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
        if (user != null) {
            String uuid = user.getUid();
            DocumentReference docRef = db.collection("users").document(uuid);
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
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
                }
                callback.getUser(null);
            });
        } else
            callback.getUser(null);

    }

    public interface GetCurrentUserCallback {

        void getUser(UserModel models);
    }
}
