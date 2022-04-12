package me.queue.smartqueue.common.async;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GetFieldsAsync {
    public GetFieldsAsync(GetFieldsCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("fields").document("ekyj839HZs6HM8uroNGm");
        docRef.get().addOnCompleteListener(task -> {
            List<String> group = (List<String>) task.getResult().get("fields");
            callback.getFields(new ArrayList<>(group));
        });
    }


    public interface GetFieldsCallback {

        void getFields(ArrayList<String> fields);
    }
}
