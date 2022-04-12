package me.queue.smartqueue.common.async;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.queue.smartqueue.common.models.UserModel;


public class GetUserDataAsync {

    public GetUserDataAsync(GetUserCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("users");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<UserModel> userModels = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    userModels.add(new Gson().fromJson(document.getData().toString(), UserModel.class));
                }
                callback.getUser(userModels);
            }
        });
    }


    public interface GetUserCallback {

        void getUser(ArrayList<UserModel> models);
    }
}
