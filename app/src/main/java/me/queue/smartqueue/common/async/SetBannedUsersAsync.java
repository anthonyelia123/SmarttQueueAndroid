package me.queue.smartqueue.common.async;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

import me.queue.smartqueue.common.SuccessListener;

public class SetBannedUsersAsync {
    public SetBannedUsersAsync(String user,SuccessListener callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> newIds = new ArrayList<>();
        new GetBannedusersAsync(ids->{
            for (String id: ids){
                newIds.add(id+",");
            }
            newIds.add(user);
            StringBuilder finalString = new StringBuilder();
            for (String id: newIds){
                finalString.append(finalString).append(id);
            }
            HashMap<String,String> map = new HashMap<>();
            map.put("banned",finalString.toString());
            db.collection("banned").document("banned")
                    .set(map).addOnCompleteListener(task -> {
                callback.isSuccess(true);
            }).addOnFailureListener(e -> {
                callback.isSuccess(false);
            });
        });

    }
}
