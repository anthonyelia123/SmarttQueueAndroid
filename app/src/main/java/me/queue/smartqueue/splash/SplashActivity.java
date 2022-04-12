package me.queue.smartqueue.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.queue.smartqueue.databinding.ActivitySplashBinding;
import me.queue.smartqueue.main.ui.activity.MainActivity;
import me.queue.smartqueue.intro.ui.activity.IntroActivity;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ActivitySplashBinding binding;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        firebaseAuth.signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                createAuthStateListener();
            }
        }, 3000);

    }


    private void createAuthStateListener() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            Log.d("log", "entered method2: " + firebaseUser);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Log.d("log", "entered method3");

            Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(mainIntent);
            finish();
        }

    }
}