package me.queue.smartqueue.intro.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import me.queue.smartqueue.login.LoginActivity;
import me.queue.smartqueue.databinding.ActivityIntroBinding;
import me.queue.smartqueue.intro.ui.adapter.ViewAdapter;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;
    ViewAdapter viewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewAdapter = new ViewAdapter(this);
        binding.viewPager.setAdapter(viewAdapter);
        binding.dot1.setViewPager(binding.viewPager);

        binding.getStarted.setOnClickListener(v -> new Handler().postDelayed(() -> {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
        }, 500));
    }

}