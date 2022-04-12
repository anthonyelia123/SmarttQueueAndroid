package me.queue.smartqueue.common.async;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

import me.queue.smartqueue.common.IdListener;

public class GetBannedusersAsync {
    public GetBannedusersAsync(IdListener callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;
        DocumentReference docRef = db.collection("banned").document("banned");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> all = new ArrayList<>();
                if (task.isSuccessful()){
                    String allUsers = task.getResult().getString("banned");

                    if (allUsers!=null){
                        all.addAll(Arrays.asList(allUsers.split(",")));

                    }
                    callback.isSuccess(all);
                }else {
                    callback.isSuccess(new ArrayList<String>());
                }
            }
        });
    }
}
