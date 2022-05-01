package me.queue.smartqueue.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.queue.smartqueue.databinding.ActivitySplashBinding;
import me.queue.smartqueue.intro.ui.activity.IntroActivity;
import me.queue.smartqueue.main.ui.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ActivitySplashBinding binding;

   @Override
   protected void onPostResume() {
    super.onPostResume();
      //firebaseAuth.signOut();
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        final Handler handler = new Handler();
        // Do something after 5s = 5000ms
        handler.postDelayed(this::createAuthStateListener, 3000);
    }


    private void createAuthStateListener() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Log.d("log", "entered method2: " + firebaseUser);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d("log", "entered method3");

            Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(mainIntent);
        }
        finish();

    }
}